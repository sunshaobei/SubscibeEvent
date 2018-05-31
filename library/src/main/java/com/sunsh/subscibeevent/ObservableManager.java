package com.sunsh.subscibeevent;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.annotations.NonNull;

/**
 * @author sunsh
 */
class ObservableManager {


    private ObservableManager() {

    }

    private Map<String, List> messageQueue = new HashMap<>();
    private static ObservableManager instance;

    public static ObservableManager getInstance() {
        //singleton
        if (instance == null) {
            synchronized (ObservableManager.class) {
                //second check
                if (instance == null) {
                    return instance = new ObservableManager();
                } else {
                    return instance;
                }
            }
        } else {
            return instance;
        }
    }


    public void addObservable2MessageQueue(@NonNull String tag, @NonNull Object o) {
        List list = messageQueue.get(tag);
        if (list == null) {
            list = new ArrayList();
            messageQueue.put(tag, list);
        }
        if (list.contains(o)) return;
        list.add(o);
    }

    public void removerObservableFormMessageQueue(@NonNull String tag, @NonNull Object o) {
        List list = messageQueue.get(tag);
        if (list == null) return;
        list.remove(o);
        if (list.size() == 0) messageQueue.remove(tag);
    }


    public List getObservablebyTag(@NonNull String tag) {
        return messageQueue.get(tag);
    }


}
