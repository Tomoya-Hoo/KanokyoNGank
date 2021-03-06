package me.tomoya.kanojyongank.base;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.tomoya.kanojyongank.R;
import me.tomoya.kanojyongank.annotation.PropertiesInject;
import me.tomoya.kanojyongank.widget.SlideLayout;

public class SimpleActivity extends AppCompatActivity {
	@Nullable @BindView(R.id.toolbar) protected Toolbar toolbar;

	protected int mContentViewId;

	/**
	 * staus of statusbar
	 */
	protected boolean mIsStatusBarTranslucent;
	/**
	 * enable exit by slide
	 */
	protected boolean mEnableSlideExit;

	private Unbinder mUbinder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getClass().isAnnotationPresent(PropertiesInject.class)) {
			PropertiesInject annotation = getClass().getAnnotation(PropertiesInject.class);
			mContentViewId = annotation.contentViewId();
			mEnableSlideExit = annotation.enableSlideExit();
			mIsStatusBarTranslucent = annotation.isStatusBarTranslucent();
		} else {
			throw new RuntimeException("Activity must add annotations of PropertiesInject.class");
		}

		setContentView(mContentViewId);
		mUbinder = ButterKnife.bind(this);
		//status
		if (toolbar != null) {
			setSupportActionBar(toolbar);
			getSupportActionBar().setDisplayShowTitleEnabled(false);
		}
		if (mIsStatusBarTranslucent) {
			Window window = this.getWindow();
			window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		}

		if (mEnableSlideExit) {
			SlideLayout slideLayout = new SlideLayout(this);
			slideLayout.bind(this);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		mUbinder.unbind();
	}
}
