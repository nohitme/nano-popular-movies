package info.ericlin.pupularmovies;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import info.ericlin.pupularmovies.discovery.MovieCategory;
import info.ericlin.pupularmovies.discovery.MoviePosterFragment;

public class MainActivity extends AppCompatActivity {

  @BindView(R.id.toolbar)
  Toolbar toolbar;

  @BindView(R.id.main_viewpager)
  ViewPager viewPager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    AndroidInjection.inject(this);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    toolbar.setTitle(R.string.app_name);
    viewPager.setAdapter(pagerAdapter);
  }

  private final FragmentStatePagerAdapter pagerAdapter =
      new FragmentStatePagerAdapter(getSupportFragmentManager()) {
        @Override
        public Fragment getItem(int position) {
          final MovieCategory category = MovieCategory.values()[position];
          return MoviePosterFragment.newInstance(category);
        }

        @Override
        public int getCount() {
          return MovieCategory.values().length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
          final int stringRes = MovieCategory.values()[position].getStringRes();
          return getString(stringRes);
        }
      };
}
