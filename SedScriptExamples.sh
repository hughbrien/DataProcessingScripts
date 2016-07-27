# Search and Replace "," with Tabs
# To esc a Tab Control-v <TAB> Key 
sed  's/\,/   /g' < ElephantOneYear.csv 

sed  's/      /\,/g ' < ./ElephantOneYearTabs.txt > ElephantOneYearNew.csv


