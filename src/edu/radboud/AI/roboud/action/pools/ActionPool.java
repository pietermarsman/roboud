package edu.radboud.ai.roboud.action.pools;

import edu.radboud.ai.roboud.RoboudController;

import java.util.Enumeration;
import java.util.Hashtable;

/**
 * An action pool prevents the wild grow of actions by keeping a list of the active (are locked) and inactive (are unlocked) ones.
 * Actions that exist for longer than 30 seconds and that are not in use are removed. When a client requests an action first an already
 * existing unlocked action is returned. When all actions are locked only than a new action is created upon request by a client.
 * Created by Mike Ligthart on 20-6-2014.
 */
public abstract class ActionPool<T> {

    public static final String TAG = "ActionPool";
    protected RoboudController controller;
    protected long expirationTime;
    private Hashtable<T, Long> locked, unlocked;

    public ActionPool(RoboudController controller) {
        this.controller = controller;
        expirationTime = 30000; // 30 seconds
        locked = new Hashtable<T, Long>();
        unlocked = new Hashtable<T, Long>();
    }

    protected abstract T create();

    public synchronized T acquire() {
        long now = System.currentTimeMillis();
        T t;
        if (unlocked.size() > 0) {
            Enumeration<T> e = unlocked.keys();
            while (e.hasMoreElements()) {
                t = e.nextElement();
                if ((now - unlocked.get(t)) > expirationTime) {
                    // object has expired
                    unlocked.remove(t);
                    t = null;
                } else {
                    //lock object and return the reference
                    unlocked.remove(t);
                    locked.put(t, now);
                    return (t);
                }
            }
        }
        // no objects available, create a new one
        t = create();
        locked.put(t, now);
        return (t);
    }

    public synchronized void release(T t) {
        locked.remove(t);
        unlocked.put(t, System.currentTimeMillis());
    }
}