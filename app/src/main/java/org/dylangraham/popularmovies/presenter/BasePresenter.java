package org.dylangraham.popularmovies.presenter;

/**
 * Abstract presenter that provides the view to the specific presenters.
 */
public class BasePresenter<T> {

    private T viewInstance;

    public BasePresenter(T viewInstance) {
        this.viewInstance = viewInstance;
    }

    protected T getView() {
        return viewInstance;
    }

    public void detachView() {
        viewInstance = null;
    }
}
