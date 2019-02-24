package com.sample.renovatio.myapplication

import android.os.Bundle
import android.support.v4.view.PagerAdapter
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.*
import android.widget.Toast
import com.bumptech.glide.Glide
import com.sample.renovatio.myapplication.Api.ApiHelper
import com.sample.renovatio.myapplication.Api.WippyService
import com.sample.renovatio.myapplication.Model.ProfileDTO
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_common_titlebar.*
import kotlinx.android.synthetic.main.content_profile.*
import kotlinx.android.synthetic.main.item_profile.view.*

class MainActivity : AppCompatActivity() {

    private lateinit var myProfileDTO: ProfileDTO

    private val disposable: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_common_titlebar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_wippy_common_back_btn_on)

        disposable.add(
            ApiHelper
                .getClient(this)
                .create(WippyService::class.java)
                .getTestUserProfile()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    myProfileDTO = it

                    initViewPager()
                    initMainProfile()
                    initDetail1Profile()
                    initDetail2Profile()

                    Log.d("success", "success: $it")
                }, {
                    Toast.makeText(this, "데이터를 불러오는데 실패했습니다.", Toast.LENGTH_LONG)
                    Log.e("error", "error: ${it.message}", it)
                })
        )


    }

    private fun initDetail2Profile() {
        detail2_religion.text = myProfileDTO.religion
        detail2_alchol.text = myProfileDTO.alcohol
        detail2_smoke.text = myProfileDTO.smoke
    }

    private fun initDetail1Profile() {
        detail1_school_text.text = myProfileDTO.education_level
        detail1_job_text.text = myProfileDTO.basic_occupation
        detail1_self_instroduction_text.text = myProfileDTO.description
    }

    private fun initMainProfile() {
        main_name_age_text.text = setNameAgeText(myProfileDTO)
        main_location_distance_text.text = setLocationDistanceText(myProfileDTO)
        main_height_blood_text.text = setHeightBloodText(myProfileDTO)
    }

    private fun initViewPager() {
        viewpager_profile.adapter = ProfilePagerAdapter(myProfileDTO.profile_images)
        indicator.setViewPager(viewpager_profile)
    }

    private fun setHeightBloodText(myProfileDTO: ProfileDTO): CharSequence? {
        return "${myProfileDTO.height}cm, ${myProfileDTO.blood_type}"
    }

    private fun setLocationDistanceText(myProfileDTO: ProfileDTO): CharSequence? {
        return "${myProfileDTO.location}, ${myProfileDTO.distance}"
    }

    private fun setNameAgeText(myProfileDTO: ProfileDTO): CharSequence? {
        return "${myProfileDTO.name}, ${myProfileDTO.age}"
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item!!.itemId) {
            R.id.action_report -> {
                Toast.makeText(this, "신고가 접수되었습니다.", Toast.LENGTH_LONG)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }
}

class ProfilePagerAdapter(private var profileImageList: List<String>) : PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(container.context)
            .inflate(R.layout.item_profile, container, false)

        Glide.with(container.context)
            .load(profileImageList[position])
            .skipMemoryCache(true)
            .into(view.imageView_profile)

        container.addView(view)

        return view
    }

    override fun isViewFromObject(view: View, any: Any): Boolean {
        return view == any
    }

    override fun getCount(): Int {
        return profileImageList.size
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

}