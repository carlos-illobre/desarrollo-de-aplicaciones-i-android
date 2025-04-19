package ar.edu.uade.deremate.di;

import javax.inject.Singleton;

import ar.edu.uade.deremate.data.repository.auth.AuthRepository;
import ar.edu.uade.deremate.data.repository.auth.AuthRetrofitRepository;
import ar.edu.uade.deremate.data.repository.route.RouteRepository;
import ar.edu.uade.deremate.data.repository.route.RouteRetrofitRepository;
import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public abstract class RouteModule {
    @Binds
    @Singleton
    public abstract RouteRepository provideRouteRepository(RouteRetrofitRepository implementation);

}
