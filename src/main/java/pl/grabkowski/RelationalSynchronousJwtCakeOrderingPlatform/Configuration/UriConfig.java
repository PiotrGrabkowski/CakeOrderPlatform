package pl.grabkowski.RelationalSynchronousJwtCakeOrderingPlatform.Configuration;

import java.util.HashSet;
import java.util.Set;

public class UriConfig {
    public static Set<String> getPublicUris(){
        Set<String> set = new HashSet<>();
        set.add("/image/gallery/images");
        return set;

    }
}
