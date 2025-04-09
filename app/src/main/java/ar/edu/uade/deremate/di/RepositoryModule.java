package ar.edu.uade.deremate.di;

import javax.inject.Singleton;

import ar.edu.uade.deremate.repository.auth.AuthRepository;
import ar.edu.uade.deremate.repository.auth.AuthRetrofitRepository;
import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public abstract class RepositoryModule {
    
    @Binds
    @Singleton
    public abstract AuthRepository provideAuthRepository(AuthRetrofitRepository implementation);
}
