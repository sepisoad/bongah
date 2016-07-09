Outfile "Bongah Yar Installer.exe"
InstallDir $PROGRAMFILES\BongaYar
 
Section
SetOutPath $INSTDIR
File /r "BongaYar\**"
CreateShortcut "$desktop\BongahYar.lnk" "$instdir\Bongah.exe"
SectionEnd


