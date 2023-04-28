package com.spotlight.platform.userprofile.api.web.modules;

import com.google.inject.Binder;
import com.google.inject.Module;

public class UserProfileApiModule implements Module {
    @Override
    public void configure(Binder binder) {
        binder.install(new JsonModule());
        binder.install(new ProfileModule());
    }
}
