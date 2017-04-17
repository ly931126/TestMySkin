package com.android.tv.settings.testmyskin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.tv.settings.testmyskin.skin.SkinChangeHelper;

public class MainActivity extends BaseActivity implements SkinChangeHelper.OnSkinChangeListener, AdapterView.OnItemClickListener {
	private static final String	TAG							= MainActivity.class.getSimpleName();
	
	private static final String	SKIN_RESOURCE_PACKAGENAME	= "com.excelent.skinresource";
	
	private ListView			mListView					= null;
	
	private String[]			mSkinNames					= new String[]{"Default", "blue", "SuperStar", "pink", "beibei", "girl"};
	private String[]			mSkinSuffixes				= new String[]{"_default", "_blue", "_super","_pink" ,"_beibei","_girl"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mListView = (ListView) findViewById(R.id.listView);
		mListView.setAdapter(new SkinAdapter());
        mListView.setOnItemClickListener(this);
	}
	
	@Override
	public void onSuccess() {
		Toast.makeText(this, "换肤成功", Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public void onError() {
		Toast.makeText(this, "换肤失败，请确定皮肤包是否安装", Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position < 0 || position >= mSkinSuffixes.length)
            return;
        SkinChangeHelper skinHelper = SkinChangeHelper.getInstance();
        switch (position) {
            case 0 :
                skinHelper.restoreDefaultSkinByAPKOrPackageOrSuffix(this);
                break;

            default :
                skinHelper.changeSkinByPackageSuffix(SKIN_RESOURCE_PACKAGENAME, mSkinSuffixes[position], this);
                break;
        }
		
	}
	
	private class SkinAdapter extends BaseAdapter {
		
		@Override
		public int getCount() {
			return mSkinNames == null ? 0 : mSkinNames.length;
		}
		
		@Override
		public String getItem(int position) {
			return mSkinNames == null ? null : mSkinNames[position];
		}
		
		@Override
		public long getItemId(int position) {
			return position;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_skin, null);
				holder.mTextView = (TextView) convertView.findViewById(R.id.text_skin_name);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.mTextView.setText(mSkinNames[position]);
			return convertView;
		}
	}
	static class ViewHolder {
		public TextView mTextView;
	}
}
