package com.kusitms.connectdog.feature.intermediator.component;

import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.material3.ExperimentalMaterial3Api;
import androidx.compose.material3.SheetState;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Modifier;
import com.kusitms.connectdog.core.model.InterApplication;
import com.kusitms.connectdog.feature.intermediator.InterManagementViewModel;
import com.kusitms.connectdog.feature.intermediator.R;

@kotlin.Metadata(mv = {1, 8, 0}, k = 2, d1 = {"\u0000$\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a$\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003H\u0003\u001a$\u0010\u0005\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003H\u0003\u001a.\u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\u0006\u0010\f\u001a\u00020\rH\u0001\u00a8\u0006\u000e"}, d2 = {"ConfirmDialog", "", "onDismiss", "Lkotlin/Function0;", "onOkClick", "RejectDialog", "VolunteerBottomSheet", "interApplication", "Lcom/kusitms/connectdog/core/model/InterApplication;", "sheetState", "Landroidx/compose/material3/SheetState;", "onDismissRequest", "viewModel", "Lcom/kusitms/connectdog/feature/intermediator/InterManagementViewModel;", "intermediator_debug"})
public final class ManagementBottomSheetKt {
    
    @androidx.compose.runtime.Composable
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    public static final void VolunteerBottomSheet(@org.jetbrains.annotations.NotNull
    com.kusitms.connectdog.core.model.InterApplication interApplication, @org.jetbrains.annotations.NotNull
    androidx.compose.material3.SheetState sheetState, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onDismissRequest, @org.jetbrains.annotations.NotNull
    com.kusitms.connectdog.feature.intermediator.InterManagementViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable
    private static final void RejectDialog(kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss, kotlin.jvm.functions.Function0<kotlin.Unit> onOkClick) {
    }
    
    @androidx.compose.runtime.Composable
    private static final void ConfirmDialog(kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss, kotlin.jvm.functions.Function0<kotlin.Unit> onOkClick) {
    }
}