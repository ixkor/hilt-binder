// Generated by @BindType. Do not modify!

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import java.lang.Float;

@Module
@InstallIn(SingletonComponent.class)
public interface HiltBinder_SingletonComponentModule {
    @Binds
    Testable<Float> bind_Test(Test binding);
}