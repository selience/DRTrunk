package me.darkeet.android.demo.task;

import android.content.Context;

/**
 * To be implemented by classes that whish to receive callbacks about task status and progress
 * updates. Note that if this class is a {@link Context}, you can use the simpler
 * {@link AsyncTaskContextHandler} instead.
 * 
 * <p>
 * It's best to not implement this interface yourself, but instead inherit from
 * {@link AsyncTaskDefaultHandler}, since it already handles the context reference for you.
 * In any case, take extreme caution to not keep a strong reference to any Context in your
 * implementation, since otherwise it will leak during Activity configuration changes! This includes
 * keeping strong references to Views and any other framework classes that bind to the current
 * context.
 * </p>
 * 
 * @author Matthias Kaeppler
 * 
 * @param <ContextT>
 * @param <ProgressT>
 * @param <ReturnT>
 */
public interface AsyncTaskHandler<ContextT extends Context, ProgressT, ReturnT> {

    ContextT getContext();

    void setContext(ContextT context);

    /**
     * Return true from this method if you want to swallow the event; it will then not be passed on
     * to the task itself.
     */
    boolean onTaskStarted(ContextT context);

    /**
     * Return true from this method if you want to swallow the event; it will then not be passed on
     * to the task itself.
     */
    boolean onTaskProgress(ContextT context, ProgressT... progress);

    /**
     * Return true from this method if you want to swallow the event; it will then not be passed on
     * to the task itself.
     */
    boolean onTaskCompleted(ContextT context, ReturnT result);

    /**
     * Return true from this method if you want to swallow the event; it will then not be passed on
     * to the task itself.
     */
    boolean onTaskSuccess(ContextT context, ReturnT result);

    /**
     * Return true from this method if you want to swallow the event; it will then not be passed on
     * to the task itself.
     */
    boolean onTaskFailed(ContextT context, Exception error);
}
