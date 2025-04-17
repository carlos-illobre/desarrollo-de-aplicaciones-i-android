package ar.edu.uade.deremate.data.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class RouteResponse implements Parcelable {
    private String id;
    private String name;
    private String description;

    public RouteResponse(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "["+ id + "] " + name;
    }

    protected RouteResponse(Parcel in) {
        id = in.readString();
        name = in.readString();
        description = in.readString();
    }

    public static final Creator<RouteResponse> CREATOR = new Creator<RouteResponse>() {
        @Override
        public RouteResponse createFromParcel(Parcel in) {
            return new RouteResponse(in);
        }

        @Override
        public RouteResponse[] newArray(int size) {
            return new RouteResponse[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(description);
    }
}
