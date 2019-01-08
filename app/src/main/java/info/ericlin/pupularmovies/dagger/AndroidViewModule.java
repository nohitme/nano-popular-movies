package info.ericlin.pupularmovies.dagger;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import info.ericlin.pupularmovies.DetailActivity;
import info.ericlin.pupularmovies.MainActivity;
import info.ericlin.pupularmovies.discovery.MoviePosterFragment;

@Module
abstract class AndroidViewModule {

  @ContributesAndroidInjector
  abstract MainActivity mainActivity();

  @ContributesAndroidInjector
  abstract DetailActivity detailActivity();

  @ContributesAndroidInjector
  abstract MoviePosterFragment moivePosterFragment();
}
