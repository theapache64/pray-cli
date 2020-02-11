echo "Downloading pray-cli.jar ..." &&
wget "https://raw.githubusercontent.com/theapache64/pray-cli/master/pray-cli.main.jar" -O "pray-cli.main.jar" -q --show-progress &&

read -p "City : " city &&
read -p "Country: " country &&
echo "0 - Shia Ithna-Ansari
1 - University of Islamic Sciences, Karachi
2 - Islamic Society of North America
3 - Muslim World League
4 - Umm Al-Qura University, Makkah
5 - Egyptian General Authority of Survey
7 - Institute of Geophysics, University of Tehran
8 - Gulf Region
9 - Kuwait
10 - Qatar
11 - Majlis Ugama Islam Singapura, Singapore
12 - Union Organization islamic de France
13 - Diyanet İşleri Başkanlığı, Turkey
14 - Spiritual Administration of Muslims of Russia
" &&

read -p "Choose calculation method (0-14): " calcMethod &&

echo "0 -  Shafi
1 - Hanafi
" &&

read -p "Choose school (0-1) : " school

jarPath=$(readlink -f pray-cli.main.jar) &&



echo "\nfunction pray(){\n\tjava -jar \"$jarPath\" $city $country $calcMethod $school \n}" >> ~/.bashrc