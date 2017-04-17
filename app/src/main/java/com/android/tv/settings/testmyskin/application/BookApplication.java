package com.android.tv.settings.testmyskin.application;

import android.app.Application;

import com.android.tv.settings.testmyskin.skin.Settings;
import com.android.tv.settings.testmyskin.skin.SkinChangeHelper;
import com.android.tv.settings.testmyskin.skin.SkinConfigHelper;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.io.File;



/**
 * Created by stefan on 2017/4/11.
 */

public class BookApplication extends Application {
	private static BookApplication mInstance = null;
	@Override
	public void onCreate() {
		super.onCreate();
        mInstance = this;
        initImageLoader();
		// init skin loader
		Settings.createInstance(mInstance);
		SkinChangeHelper.getInstance().init(mInstance);
		// restore skin in last time exit
		SkinChangeHelper.getInstance().changeSkinByPackageSuffix(SkinConfigHelper.getSkinIdentifier(), SkinConfigHelper.getSkinIdentifierSuffix(), null);
	}

    public static BookApplication getInstance() {
        return mInstance;
    }

    private void initImageLoader() {
		DisplayImageOptions options = new DisplayImageOptions.Builder().cacheOnDisk(true).build();
		File cacheDir = new File(mInstance.getCacheDir()+ "/imageLoader/cache");
		ImageLoaderConfiguration.Builder configuration = new ImageLoaderConfiguration.Builder(mInstance);
		configuration.threadPriority(Thread.NORM_PRIORITY - 2);
		configuration.denyCacheImageMultipleSizesInMemory();
		configuration.diskCache(new UnlimitedDiskCache(cacheDir));
		configuration.diskCacheFileNameGenerator(new Md5FileNameGenerator());
		configuration.diskCacheSize(50 * 1024 * 1024);
		configuration.tasksProcessingOrder(QueueProcessingType.LIFO);
		configuration.writeDebugLogs();
		configuration.defaultDisplayImageOptions(options);
		ImageLoader.getInstance().init(configuration.build());
	}
}
