package info.ericlin.moviedb.model;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.Json;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.util.List;

import io.reactivex.annotations.NonNull;

@AutoValue
public abstract class Configuration {

    @NonNull
    public abstract Images images();

    public static JsonAdapter<Configuration> jsonAdapter(Moshi moshi) {
        return new AutoValue_Configuration.MoshiJsonAdapter(moshi);
    }

    @AutoValue
    public static abstract class Images {

        @NonNull
        @Json(name = "secure_base_url")
        public abstract String secureBaseUrl();

        @NonNull
        @Json(name = "poster_sizes")
        public abstract List<String> posterSizes();

        public static JsonAdapter<Images> jsonAdapter(Moshi moshi) {
            return new AutoValue_Configuration_Images.MoshiJsonAdapter(moshi);
        }
    }

}
