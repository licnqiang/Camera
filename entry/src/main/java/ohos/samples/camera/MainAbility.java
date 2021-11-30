/*
 * Copyright (c) 2021 Huawei Device Co., Ltd.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ohos.samples.camera;

import ohos.aafwk.content.Operation;
import ohos.agp.components.Component;
import ohos.agp.components.Image;
import ohos.agp.window.dialog.ToastDialog;
import ohos.media.image.ImageSource;

import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;
import ohos.bundle.IBundleManager;
import ohos.security.SystemPermission;

import java.util.Arrays;

/**
 * MainAbility
 */
public class MainAbility extends Ability {
    private Image image;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_main_ability_slice);
        requestPermissions();
        initComponents();
    }

    private void requestPermissions() {
        String[] permissions = {
                SystemPermission.WRITE_USER_STORAGE, SystemPermission.READ_USER_STORAGE, SystemPermission.CAMERA,
                SystemPermission.MICROPHONE, SystemPermission.LOCATION
        };
        requestPermissionsFromUser(Arrays.stream(permissions)
                .filter(permission -> verifySelfPermission(permission) != IBundleManager.PERMISSION_GRANTED).toArray(String[]::new), 0);
    }

    @Override
    protected void onAbilityResult(int requestCode, int resultCode, Intent resultData) {
        super.onAbilityResult(requestCode, resultCode, resultData);
        new ToastDialog(this).setText("sgewgwegwe").show();
        if (resultCode == 1004) {
            String phth = resultData.getStringParam("phth");
            ImageSource imageSource=ImageSource.create(phth,null);
            image.setPixelMap(imageSource.createPixelmap(null));
            new ToastDialog(this).setText(phth).show();
        }
    }

    @Override
    public void onRequestPermissionsFromUserResult(int requestCode, String[] permissions, int[] grantResults) {
        if (permissions == null || permissions.length == 0 || grantResults == null || grantResults.length == 0) {
            return;
        }
        for (int grantResult : grantResults) {
            if (grantResult != IBundleManager.PERMISSION_GRANTED) {
                terminateAbility();
                break;
            }
        }
    }

    private void initComponents() {
        Component takePhoto = findComponentById(ResourceTable.Id_take_photo);
        image = (Image)findComponentById(ResourceTable.Id_image);
        takePhoto.setClickedListener((component) -> startAbility(TakePhotoAbility.class.getName()));
    }

    private void startAbility(String abilityName) {
        Operation operation = new Intent.OperationBuilder()
                .withDeviceId("")
                .withBundleName(getBundleName())
                .withAbilityName(abilityName)
                .build();
        Intent intent = new Intent();
        intent.setOperation(operation);
        startAbilityForResult(intent,1004);
    }
}
