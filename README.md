<div align="center">
  <img src="metadata/en-US/images/icon.svg" alt="Ringtone Widgets Icon"/>

# Ringtone Widgets

[![F-Droid](https://img.shields.io/f-droid/v/io.github.dleprovost.ringtonewidgets?logo=f-droid&label=F-Droid&color=green&style=plastic)](https://f-droid.org/)
[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg?logo=gplv3&style=plastic)](https://www.gnu.org/licenses/gpl-3.0)
[![Release](https://img.shields.io/github/release/dleprovost/ringtonewidgets?logo=github&label=Release&style=plastic)](https://github.com/dleprovost/ringtone-widgets/releases)

</div>

**Ringtone Widgets** is a minimalist Android app that provides quick home-screen widgets to switch between **Ringer**, **Vibrate**, and **Silent** modes.  

The app is designed to be **privacy-friendly**:  
- No data collection  
- No analytics  
- No ads  
- Requires only **Do Not Disturb (DND) access** due to Android restrictions on ringer mode control  


<div align="center">
  <a href="https://github.com/dleprovost/ringtone-widgets/releases"><img alt="Get it on GitHub" src="https://raw.githubusercontent.com/rubenpgrady/get-it-on-github/ac79a7bae5fe83c73bab240b1933665e3f97e466/get-it-on-github.png" height="80"/></a>
  <a href="https://f-droid.org/packages/io.github.dleprovost.ringtonewidgets"><img src="https://f-droid.org/badge/get-it-on.png" alt="Get it on F-Droid" height="80"></a>
</div>


## Features

- Lightweight and minimalist widgets (1x2 and 2x1)  
- Three buttons:  
  - Ringer  
  - Vibrate  
  - Silent (mapped to *Do Not Disturb* depending on Android version)  
- Color customization for widget icons  
- English and French localization so far
- Open-source, no background services  


## Limitations

Due to Android system restrictions:  

- The **true “silent” mode** available in the system volume panel is not accessible programmatically. Widgets use *Do Not Disturb (DND)* as a fallback.  
- Widget state does **not automatically update** if the ringer mode is changed elsewhere (e.g., hardware volume buttons).  

These behaviors are **expected and normal**.  


## Screenshots

<div align="center">
<img src="metadata/en-US/images/phoneScreenshots/1.png" width="250" alt="Screenshot3">
<img src="metadata/en-US/images/phoneScreenshots/2.png" width="250" alt="Screenshot3">
<img src="metadata/en-US/images/phoneScreenshots/3.png" width="250" alt="Screenshot3">
</div>


## Permissions

- **Do Not Disturb Access (ACCESS_NOTIFICATION_POLICY)**  
  - Required to modify the ringer mode through the widgets  
  - No other permissions are used  


## Contributing

Contributions are welcome!

* Fork this repository
* Make your changes
* Open a Pull Request

👉 Repository: [https://github.com/dleprovost/ringtone-widgets](https://github.com/dleprovost/ringtone-widgets)


## License

This project is licensed under the **GNU General Public License v3.0 (GPLv3)**.  
See the [LICENSE](LICENSE) file for details.


## Acknowledgments

Thanks for supporting **privacy-friendly** open-source Android apps 🙌
