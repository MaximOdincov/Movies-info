package com.example.movies;
import android.app.Application;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import java.util.List;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainViewModel extends AndroidViewModel {
    private static final String TAG = "MainViewModel";
    private int page = 1;
    private MutableLiveData<List<Movie>> movies = new MutableLiveData<List<Movie>>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    public MainViewModel(@NonNull Application application) {
        super(application);
        loadMovies();
    }

    public LiveData<List<Movie>> getMovies() {
        return movies;
    }

    public LiveData<Boolean> getIsLoadingNow() {
        return isLoadingNow;
    }

    public MutableLiveData<Boolean> isLoadingNow = new MutableLiveData<>(false);

    public void loadMovies(){
        Boolean loading = isLoadingNow.getValue();
        if(loading!=null && loading){
            return;
        }
        Disposable disposable = (APIFactory.apiService.loadMovie(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Throwable {
                        isLoadingNow.setValue(true);
                    }
                }).doAfterTerminate(new Action() {
                    @Override
                    public void run() throws Throwable {
                        isLoadingNow.setValue(false);
                    }
                }).subscribe(new Consumer<APIResponse>() {
                    @Override
                    public void accept(APIResponse apiResponse) throws Throwable {
                        List<Movie> loadedMovies = movies.getValue();
                        if(loadedMovies!=null){
                            loadedMovies.addAll(apiResponse.getMovies());
                            movies.setValue(loadedMovies);
                        }
                        else{
                            movies.setValue(apiResponse.getMovies());
                        }
                        page++;
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Log.d(TAG,throwable.toString());
                    }
                }));
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onCleared() {
        compositeDisposable.dispose();
        super.onCleared();
    }
}
