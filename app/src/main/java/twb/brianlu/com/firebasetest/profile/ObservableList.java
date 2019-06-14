package twb.brianlu.com.firebasetest.profile;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class ObservableList<T> {

  protected final PublishSubject<T> onAdd;

  public ObservableList() {

    this.onAdd = PublishSubject.create();
  }

  public static void main(String[] args) throws Exception {
    //        ObservableList<List<String>> olist = new ObservableList<>();
    //
    //        olist.getObservable().subscribe(new Observer<List<String>>() {
    //            @Override
    //            public void onSubscribe(Disposable d) {
    //
    //            }
    //
    //            @Override
    //            public void onNext(List<String> strings) {
    //
    //            }
    //
    //            @Override
    //            public void onError(Throwable e) {
    //
    //            }
    //
    //            @Override
    //            public void onComplete() {
    //
    //            }
    //        });
    //
    //
    //        olist.add(new ArrayList<String>());
    //        Thread.sleep(1000);
    //        olist.add(new ArrayList<String>());
    //        Thread.sleep(1000);
    //        olist.add(new ArrayList<String>());

  }

  public void onNext(T value) {
    onAdd.onNext(value);
  }

  public Observable<T> getObservable() {
    return onAdd;
  }
}
