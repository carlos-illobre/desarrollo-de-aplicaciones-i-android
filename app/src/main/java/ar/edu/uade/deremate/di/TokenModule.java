package ar.edu.uade.deremate.di;
import android.content.Context;

import javax.inject.Singleton;

import ar.edu.uade.deremate.data.repository.token.TokenRepository;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class TokenModule {

    @Provides
    @Singleton
    public TokenRepository provideTokenRepository(@ApplicationContext Context context) {
        return new TokenRepository(context);
    }
}
