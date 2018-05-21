package io.digibyte.presenter.activities.intro;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.MenuItem;

import io.digibyte.R;
import io.digibyte.databinding.ActivityWriteDownBinding;
import io.digibyte.presenter.activities.callbacks.ActivityWriteDownCallback;
import io.digibyte.presenter.activities.util.BRActivity;
import io.digibyte.presenter.interfaces.BRAuthCompletion;
import io.digibyte.tools.animation.BRAnimator;
import io.digibyte.tools.security.AuthManager;
import io.digibyte.tools.security.PostAuth;

public class WriteDownActivity extends BRActivity implements BRAuthCompletion {

    private ActivityWriteDownCallback callback = () -> AuthManager.getInstance().authPrompt(
            WriteDownActivity.this, null,
            getString(R.string.VerifyPin_continueBody), new AuthType(AuthType.Type.POST_AUTH));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityWriteDownBinding binding = DataBindingUtil.setContentView(this,
                R.layout.activity_write_down);
        binding.setCallback(callback);
        setupToolbar();
        setToolbarTitle(R.string.SecurityCenter_paperKeyTitle);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.home:
            case android.R.id.home:
                BRAnimator.openBreadActivity(WriteDownActivity.this, false);
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        BRAnimator.openBreadActivity(WriteDownActivity.this, false);
    }

    @Override
    public void onComplete(AuthType authType) {
        switch(authType.type) {
            case LOGIN:
                break;
            case DIGI_ID:
                break;
            case POST_AUTH:
                PostAuth.getInstance().onPhraseCheckAuth(WriteDownActivity.this,false);
                break;
            default:
                super.onComplete(authType);
        }
    }

    @Override
    public void onCancel(AuthType authType) {

    }
}