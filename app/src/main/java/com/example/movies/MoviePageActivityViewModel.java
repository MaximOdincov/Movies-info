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

public class MoviePageActivityViewModel extends AndroidViewModel {
    private static final String TAG = "MoviePageActivityViewModel";
    private int page = 1;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final MutableLiveData<List<Trailer>> trailers  = new MutableLiveData();
    private final MutableLiveData<List<Review>> reviews = new MutableLiveData<>();

    private final DataBaseDao movieDao;

    public LiveData<Boolean> getIsLoadingNow() {
        return isLoadingNow;
    }

    private final MutableLiveData<Boolean> isLoadingNow = new MutableLiveData<>();
    public MoviePageActivityViewModel(@NonNull Application application) {
        super(application);
        movieDao = MovieDatabase.getInstance(application).movieDao();
    }

    public LiveData<Movie> getFavouriteMovie(int movieId){
        return movieDao.getFavouriteMovie(movieId);
    }

    public LiveData<List<Movie>> getFavouriteMovies(){
        return movieDao.getFavouriteMovies();
    }

    public void insertMovie(Movie movie){
        Disposable disposable = movieDao.insertMovie(movie).subscribeOn(Schedulers.io()).subscribe();
        compositeDisposable.add(disposable);
    }

    public void removeMovie(int movieId){
        Disposable disposable = movieDao.removeMovie(movieId).subscribeOn(Schedulers.io()).subscribe();
        compositeDisposable.add(disposable);
    }

    public LiveData<List<Trailer>> getTrailers() {
        return trailers;
    }

    public LiveData<List<Review>> getReview(){
        return reviews;
    }

    public void loadTrailers(int id){
        Disposable disposable = APIFactory.apiService.loadTrailers(id)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<APIMovieResponse>() {
                    @Override
                    public void accept(APIMovieResponse apiMovieResponse) throws Throwable {
                        Log.d(TAG, apiMovieResponse.toString());
                        if (apiMovieResponse.getVideo() != null) {
                            trailers.setValue(apiMovieResponse.getVideo().getTrailers());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Log.d(TAG, throwable.toString());
                    }
                });
        compositeDisposable.add(disposable);
    }

    public void loadReview(int id){
        Boolean loading = isLoadingNow.getValue();
        if(loading!=null && loading){
            return;
        }
        Disposable disposable = APIFactory.apiService.loadReviews(id, page)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Throwable {
                        isLoadingNow.setValue(true);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(new Action() {
                    @Override
                    public void run() throws Throwable {
                        isLoadingNow.setValue(false);
                    }
                })
                .subscribe(new Consumer<APIReviewResponse>() {
                    @Override
                    public void accept(APIReviewResponse apiReviewResponse) throws Throwable {
                        List<Review> loadedReview = reviews.getValue();
                        if(loadedReview!=null){
                            loadedReview.addAll(apiReviewResponse.getReviews());
                            reviews.setValue(loadedReview);
                        }
                        else{
                            reviews.setValue(apiReviewResponse.getReviews());
                        }
                        page++;
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Log.d(TAG, throwable.toString());
                    }
                });
        compositeDisposable.add(disposable);
    }



    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
