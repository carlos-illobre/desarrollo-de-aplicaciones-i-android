package ar.edu.uade.deremate.di;

import javax.inject.Singleton;

import ar.edu.uade.deremate.data.repository.entregas.EntregaRepository;
import ar.edu.uade.deremate.data.repository.entregas.EntregaRetrofitRepository;
import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public abstract class EntregasModule {
    @Binds
    @Singleton
    public abstract EntregaRepository provideEntregaRepository(EntregaRetrofitRepository implementation);

}
