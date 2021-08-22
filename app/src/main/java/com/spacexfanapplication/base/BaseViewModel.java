package com.spacexfanapplication.base;

import androidx.lifecycle.ViewModel;

import org.jetbrains.annotations.NotNull;
import org.koin.core.Koin;
import org.koin.core.KoinComponent;

public class BaseViewModel extends ViewModel implements KoinComponent {
    @NotNull
    @Override
    public Koin getKoin() {
        return KoinComponent.DefaultImpls.getKoin(this);
    }
}
