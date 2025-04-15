package ar.edu.uade.deremate.di;

import ar.edu.uade.deremate.data.repository.auth.AuthRepository;
import ar.edu.uade.deremate.data.repository.auth.AuthRetrofitRepository;
import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import javax.inject.Singleton;

@Module
@InstallIn(SingletonComponent.class)
public abstract class AuthModule {
    
    @Binds
    @Singleton
    public abstract AuthRepository provideAuthRepository(AuthRetrofitRepository implementation);
}
