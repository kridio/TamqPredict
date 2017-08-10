package tw.gov.epa.taqmpredict.map;

/**
 * Created by user on 2017/8/9.
 */

public final class LatLng{
    public final double latitude;
    public final double longitude;
    public final int x;
    public final int y;

    public LatLng(double lat, double lng,int x,int y) {
        if(-180.0D <= lng && lng < 180.0D) {
            this.longitude = lng;
        } else {
            this.longitude = ((lng - 180.0D) % 360.0D + 360.0D) % 360.0D - 180.0D;
        }

        this.latitude = Math.max(-90.0D, Math.min(90.0D, lat));
        this.x = x;
        this.y = y;
    }

    public final int hashCode() {
        long var2 = Double.doubleToLongBits(this.latitude);
        int var1 = 31 + (int)(var2 ^ var2 >>> 32);
        var2 = Double.doubleToLongBits(this.longitude);
        return var1 * 31 + (int)(var2 ^ var2 >>> 32);
    }

    public final boolean equals(Object var1) {
        if(this == var1) {
            return true;
        } else if(!(var1 instanceof LatLng)) {
            return false;
        } else {
            LatLng var2 = (LatLng)var1;
            return Double.doubleToLongBits(this.latitude) == Double.doubleToLongBits(var2.latitude) && Double.doubleToLongBits(this.longitude) == Double.doubleToLongBits(var2.longitude);
        }
    }

    public final String toString() {
        double var1 = this.latitude;
        double var3 = this.longitude;
        return (new StringBuilder(60)).append("lat/lng: (").append(var1).append(",").append(var3).append(")").toString();
    }
}
