package com.spacexfanapplication.base;

import android.os.Bundle;

import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.HashMap;

import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;

public abstract class HBaseFragment <VM extends BaseViewModel, DB extends ViewDataBinding> extends BaseFragment{

    protected  final String TAG = this.getClass().getSimpleName();
    public VM viewModel;
    protected DB binding;
    private boolean hasCreated;
    private boolean allowReinit;
    private HashMap _$_findViewCache;

    protected final boolean getHasCreated() {
        return this.hasCreated;
    }

    protected final void setHasCreated(boolean hasCreated) {
        this.hasCreated = hasCreated;
    }

    protected final boolean getAllowReinit() {
        return this.allowReinit;
    }

    protected final void setAllowReinit(boolean allowReinit) {
        this.allowReinit = allowReinit;
    }

    public abstract void init();

    public abstract void setUpListeners();

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        if (!hasCreated || allowReinit) {
            init();
            setUpListeners();
        }

        hasCreated = true;
        super.onActivityCreated(savedInstanceState);
    }

    public final void setup(Class<VM> viewModel,DB dataBinding) {
        this.viewModel = (VM) ViewModelProviders.of(this).get(viewModel);
        binding = dataBinding;
        dataBinding.setLifecycleOwner(getViewLifecycleOwner());
        setViewModel();
    }

    public final void setup(@Nullable Fragment fragment, @NotNull Class<VM> viewModel, @NotNull DB dataBinding) {
        if (fragment != null) {
            this.viewModel = (VM) ViewModelProviders.of(fragment).get(viewModel);
        } else {
            this.viewModel = (VM) ViewModelProviders.of(this).get(viewModel);
        }
        binding = dataBinding;
        dataBinding.setLifecycleOwner(this);
        setViewModel();
    }


    protected final void setup(@Nullable Fragment fragment, @NotNull Class<VM> viewModel) {
        if (fragment != null) {
            this.viewModel = (VM) ViewModelProviders.of(fragment).get(viewModel);
        } else {
            this.viewModel = (VM) ViewModelProviders.of(this).get(viewModel);
        }
    }

    protected final void setUpViewModel(@NotNull FragmentActivity activity, @NotNull Class<VM> viewModel) {
        this.viewModel = (VM) ViewModelProviders.of(activity).get(viewModel);
    }

    protected final void setup(@NotNull DB dataBinding) {
        binding = dataBinding;
        dataBinding.setLifecycleOwner(this);
        setViewModel();
    }

    public final void setViewModel() {
        Field[] var12 = binding.getClass().getDeclaredFields();
        Field[] fields = var12;
        if (binding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        }

        Class superclass = binding.getClass().getSuperclass();
        Field[] parentFields = superclass != null ? superclass.getDeclaredFields() : null;
        if (parentFields != null) {
            fields = ArraysKt.plus(fields, parentFields);
        }
        Field field = null;

        for (Field field1 : fields) {
            field1.setAccessible(true);
            BaseViewModel var10001 = viewModel;
            field1.getType();
            if (field1.getType().isAssignableFrom(var10001.getClass())) {
                field = field1;
            }
        }

        if (field != null) {
            try {
                field.set(binding, viewModel);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

    }

    public HBaseFragment() {
        allowReinit = true;
    }
}
