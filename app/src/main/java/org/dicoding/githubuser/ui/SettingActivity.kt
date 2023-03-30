package org.dicoding.githubuser.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.google.android.material.switchmaterial.SwitchMaterial
import org.dicoding.githubuser.R
import org.dicoding.githubuser.databinding.ActivitySettingBinding
import org.dicoding.githubuser.factory.SettingViewModelFactory
import org.dicoding.githubuser.preferences.SettingPreferences
import org.dicoding.githubuser.viewModels.SettingViewModel


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class SettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding
    private val settingViewModel by viewModels<SettingViewModel>{ SettingViewModelFactory(SettingPreferences.getInstance(dataStore)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Pengaturan"

        val switchTheme = findViewById<SwitchMaterial>(R.id.switch_theme)

        settingViewModel.getThemeSettings().observe(this){ isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        }

        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            settingViewModel.saveThemeSetting(isChecked)
        }
    }
}