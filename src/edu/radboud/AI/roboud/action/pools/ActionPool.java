package edu.radboud.ai.roboud.action.pools;

import edu.radboud.ai.roboud.RoboudController;

import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Created by mikel_000 on 20-6-2014.
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