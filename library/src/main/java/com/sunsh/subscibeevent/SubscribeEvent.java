package com.sunsh.subscibeevent;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;


/**
 * @author sunsh
 * Whenever you publish an event into your bus,
 * change the code to expose anObservable.
 * Whenever you listen to an event from the bus,
 * change the codeto subscribe to the Observables you have exposed.
 * -by jakeWharton
 */

public class SubscribeEvent {

    private ExecutorService singleThread = Executors.newSingleThreadExecutor();
    private Handler handler = new Handler(Looper.getMainLooper());

    private SubscribeEvent() {
    }


    private static SubscribeEvent instance;

    public static SubscribeEvent getInstance() {
        //singleton
        if (instance == null) {
            synchronized (SubscribeEvent.class) {
                //second check
                if (instance == null) {
                    return instance = new SubscribeEvent();
                } else {
                    return instance;
                }
            }
        } else {
            return instance;
        }
    }

    /**
     * @param tag  the event's tag (name)
     * @param args event'args
     */
    public void exposeEvent(@NonNull final String tag, @Nullable final Object... args) {
        final List observables = ObservableManager.getInstance().getObservablebyTag(tag);
        if (observables == null) return;
        //TODO
        singleThread.submit(new Runnable() {
            @Override
            public void run() {
                notifyAllObservers(tag, observables, args);
            }
        });
    }

    /**
     * @param tag the event's tag (name)
     * @param o   observable
     */
    public void subscribe(@NonNull final String tag, @NonNull final Object o) {
        singleThread.submit(new Runnable() {
            @Override
            public void run() {
                ObservableManager.getInstance().addObservable2MessageQueue(tag, o);
            }
        });
    }

    /**
     * @param tag the event's tag (name)
     * @param o   observable
     */
    public void removeuObservable(@NonNull final String tag,@NonNull final Object o) {
        singleThread.submit(new Runnable() {
                @Override
                public void run() {
                ObservableManager.getInstance().removerObservableFormMessageQueue(tag, o);
            }
        });
    }


    /**
     * notify event
     *
     * @param strMethod method name
     * @param listeners all observable by tag -> strMethod
     * @param args      method parameters
     */
    private void notifyAllObservers(String strMethod, List listeners, Object... args) {
        List<Class> argTypeList = new ArrayList<>();
        List<Object> argValueList = new ArrayList<>();
        int argCount = 0;
        if (args != null) {
            for (Object arg : args) {
                argTypeList.add(arg.getClass());
                argValueList.add(arg);
                ++argCount;
            }
        }
        Class[] argTypes = new Class[argCount];
        Object[] argValues = new Object[argCount];
        if (!argTypeList.isEmpty())
            argTypes = argTypeList.toArray(argTypes);
        if (!argValueList.isEmpty())
            argValues = argValueList.toArray();
        for (final Object listener : listeners) {
            if (listener != null) {
                Class cls = listener.getClass();
                try {
                    final Method method = cls.getDeclaredMethod(strMethod, argTypes);
                    method.setAccessible(true);
                    if (method != null) {
                        final Object[] finalArgValues = argValues;
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    method.invoke(listener, finalArgValues);
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                } catch (InvocationTargetException e) {
                                    Log.e("SubscribeEvent", e.toString());
                                }
                            }
                        });
                    }
                } catch (Exception e) {
                    Log.e("SubscribeEvent", e.toString());
                }
            }
        }
    }

}
