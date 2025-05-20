# CHECKLIST-ANDROID-APP

In Navigation Bar TO Add New Elements :

1. Fragment and Viewmodel in ui
2. icon in drawable
3. Add a file in res/layout ex: for settings 'fragment_settings.xml
4. In res/menu activity_main_drawer.xml change this
         <item
            android:id="@+id/nav_settings"
            android:icon="@drawable/ic_menu_settings"
            android:title="@string/menu_settings" />
5. res/navigation add this : for example settings
<fragment
        android:id="@+id/nav_settings"
        android:name="com.example.checklist_android_app.ui.settings.SettingsFragment"
        android:label="@string/menu_settings"
        tools:layout="@layout/fragment_settings" />
6. res/values in string.xml add this
<string name="menu_settings">Settings</string>