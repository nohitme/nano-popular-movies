package info.ericlin.pupularmovies.dagger;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;
import info.ericlin.pupularmovies.discovery.MoviePosterViewModel;
import info.ericlin.pupularmovies.discovery.MoviePosterViewModelFactory;
import info.ericlin.pupularmovies.factory.ViewModelFactory;

@Module
abstract class ViewModelModule {

  @Binds
  @IntoMap
  @ClassKey(MoviePosterViewModel.class)
  abstract ViewModelFactory moviePosterViewModelFactory(MoviePosterViewModelFactory factory);
}
