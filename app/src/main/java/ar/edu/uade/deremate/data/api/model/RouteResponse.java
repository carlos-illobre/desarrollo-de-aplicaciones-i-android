package ar.edu.uade.deremate.data.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class RouteResponse implements Parcelable {
    private String packageId;
    private String warehouse;
    private String destinationNeighborhood;

    public RouteResponse(String packageId, String warehouse, String destinationNeighborhood) {
        this.packageId = packageId;
        this.warehouse = warehouse;
        this.destinationNeighborhood = destinationNeighborhood;
    }

    public String getPackageId() {
        return packageId;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public String getDestinationNeighborhood() {
        return destinationNeighborhood;
    }

    @Override
    public String toString() {
        return warehouse;
    }

    protected RouteResponse(Parcel in) {
        packageId = in.readString();
        warehouse = in.readString();
        destinationNeighborhood = in.readString();
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
        dest.writeString(packageId);
        dest.writeString(warehouse);
        dest.writeString(destinationNeighborhood);
    }
}
