package com.ordolabs.clipit.data.models.clip;

import com.ordolabs.clipit.data.models.base.BaseMvpModel;
import com.ordolabs.clipit.ui.clip.ClipMvpContract;

/**
 * Created by ordogod on 03.07.19.
 **/

interface ClipModelContract<P extends ClipMvpContract.Presenter> extends BaseMvpModel<P> {
}
