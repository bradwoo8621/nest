/**
 * Code Tables
 */
var Codes = {
    PartyType: new CodeTable([
        {id: "I", text: "Individual"},
        {id: "O", text: "Organization"}
    ]),
    RoleType: new CodeTable([
        {id: "BNK", text: "Bank"},
        {id: "AGI", text: "Agent Individual"},
        {id: "AGO", text: "Agent Organization"},
        {id: "CTI", text: "Customer Individual"},
        {id: "CTO", text: "Customer Organization"},
        {id: "BRH", text: "Branch"},
        {id: "DPT", text: "Department"},
        {id: "EMP", text: "Employee"},
    ], null, new CodeTableSorter()),
    LegalType: new CodeTable([
        {id: "1", text: "Sole Proprietor"},
        {id: "2", text: "Private Limited"},
        {id: "3", text: "Partnership"},
        {id: "4", text: "Public Listed"},
        {id: "5", text: "Cooperatives"},
        {id: "99", text: "Other"}
    ]),
    OrganizationIdType: new CodeTable([
        {id: "1", text: "ID"},
        {id: "99", text: "Virtual Number"}
    ]),
    IndividualIdType: new CodeTable([
        {id: "1", text: "ID Card"},
        {id: "2", text: "Residence Permit"},
        {id: "3", text: "Military ID"},
        {id: "4", text: "Passport"},
        {id: "5", text: "Drive License"},
        {id: "99", text: "Virtual Number"}
    ]),
    Gender: new CodeTable([
        {id: "F", text: "Female"},
        {id: "M", text: "Male"}
    ]),
    Marital: new CodeTable([
        {id: "S", text: "Single"},
        {id: "M", text: "Married"}
    ]),
    Race: new CodeTable([
        {id: "C", text: "Caucasion"},
        {id: "M", text: "Mongoliod"},
        {id: "N", text: "Negriod"},
    ]),
    Religion: new CodeTable([
        {id: "1", text: "Christianity"},
        {id: "2", text: "Catholicism"},
        {id: "3", text: "Protestantism"},
        {id: "10", text: "Islam"},
        {id: "11", text: "Shiism"},
        {id: "12", text: "Sunnism"},
        {id: "21", text: "Judaism"},
        {id: "31", text: "Buddhism"},
        {id: "32", text: "Hinduism"},
        {id: "41", text: "Manichaeism"},
        {id: "42", text: "Zoroastrianism"},
        {id: "51", text: "CaoDai"},
        {id: "61", text: "Confucianism"},
        {id: "62", text: "IKuanTao"},
        {id: "63", text: "Mohism"},
        {id: "64", text: "Taoism"},
        {id: "71", text: "Shinto"},
        {id: "99", text: "Other"}
    ], null, new CodeTableSorter("99")),
    Occupation: new CodeTable([
        {id: "1", text: "Agricultural Inspector"},
        {id: "2", text: "Bush Pilot"},
        {id: "3", text: "Farm Manager or Owner"},
        {id: "4", text: "Fisherman"},
        {id: "5", text: "Laborer"},
        {id: "6", text: "Scientist"},
        {id: "7", text: "Supervisor"},
        {id: "8", text: "Techinician"},
        {id: "9", text: "Actor"},
        {id: "10", text: "Administrator"},
        {id: "11", text: "Broadcaster"},
        {id: "12", text: "Cameraman"},
        {id: "13", text: "Commercial Artist"},
        {id: "14", text: "Film Editor"},
        {id: "15", text: "Illustrator"},
        {id: "16", text: "Editor"},
        {id: "18", text: "Director"},
        {id: "19", text: "Executive"},
        {id: "20", text: "HR"},
        {id: "21", text: "Office Cashier"},
        {id: "22", text: "Assistant"},
        {id: "23", text: "Clerk"},
        {id: "24", text: "Planner"},
        {id: "25", text: "Auto Salesman"},
        {id: "26", text: "Appraiser"},
        {id: "27", text: "Owner"},
        {id: "28", text: "Repairer"},
        {id: "29", text: "Service"},
        {id: "30", text: "Animal Trainer"},
        {id: "31", text: "Barber"},
        {id: "32", text: "Beauty Shop Owner"},
        {id: "33", text: "Day Care Worker"},
        {id: "34", text: "Housekeeper"},
        {id: "35", text: "Maid"},
        {id: "36", text: "Tailor"},
        {id: "38", text: "Business Operator"},
        {id: "39", text: "Computer Programmer"},
        {id: "40", text: "Consultant or Adviser"},
        {id: "41", text: "Project Manager"},
        {id: "42", text: "Analyst"},
        {id: "44", text: "Manager"},
        {id: "45", text: "Architect"},
        {id: "46", text: "Butcher"},
        {id: "47", text: "Fishman"},
        {id: "48", text: "Jeweler"},
        {id: "50", text: "Life Guard"},
        {id: "51", text: "Plumber"},
        {id: "53", text: "Advisor"},
        {id: "54", text: "Case or Social Worker"},
        {id: "55", text: "Education Counselor"},
        {id: "56", text: "Psychologist"},
        {id: "57", text: "Aide"},
        {id: "58", text: "Counselor"},
        {id: "59", text: "Coaches"},
        {id: "60", text: "Fitness Trainer"},
        {id: "61", text: "Instructor"},
        {id: "62", text: "Teacher"},
        {id: "63", text: "Tutor"},
        {id: "67", text: "Electrical Engineer"},
        {id: "68", text: "Flight Engineer"},
        {id: "69", text: "Mechanical Engineer"},
        {id: "70", text: "Engineer"},
        {id: "71", text: "Accounting Supervisor"},
        {id: "72", text: "Auto Dealer"},
        {id: "73", text: "Bank Manager"},
        {id: "74", text: "Bar Owner"},
        {id: "76", text: "VP-Business Vice President"},
        {id: "77", text: "White Collar President"},
        {id: "80", text: "Agent"},
        {id: "81", text: "Bank Officer"},
        {id: "82", text: "Financial Analyst"},
        {id: "83", text: "Loan Officer"},
        {id: "88", text: "Certified Nursing Assistant"},
        {id: "89", text: "Dentist"},
        {id: "90", text: "Hosipital Administrator"},
        {id: "91", text: "White Collar Inspector"},
        {id: "95", text: "Court Officer"},
        {id: "96", text: "Judge"},
        {id: "97", text: "Lawyer"},
        {id: "98", text: "Legal Assistant"},
        {id: "99", text: "Policeman"},
        {id: "100", text: "Inspector"},
        {id: "999", text: "Other"}
    ], null, new CodeTableSorter("999")),
    AddressType: new CodeTable([
        {id: "1", text: "Home"},
        {id: "2", text: "Communicate"},
        {id: "3", text: "Company"},
        {id: "99", text: "Other"}
    ], null, new CodeTableSorter("99")),
    AccountType: new CodeTable([
        {id: "1", text: "Auto Debit"},
        {id: "2", text: "Credit Card"}
    ]),
    CreditCardType: new CodeTable([
        {id: "1", text: "American Express"},
        {id: "2", text: "Diners Club"},
        {id: "3", text: "Discover"},
        {id: "4", text: "JCB"},
        {id: "5", text: "Master"},
        {id: "6", text: "VISA"},
        {id: "7", text: "Unipay"},
        {id: "99", text: "Other"}
    ], null, new CodeTableSorter("99"))
};
/**
 * regions
 */
var Regions = {
    Country: new CodeTable([
        /* {id: "AFG", tel: "93", name: "AFGHANISTAN"},
         {id: "ALB", tel: "355", name: "ALBANIA"},
         {id: "DZA", tel: "213", name: "ALGERIA"},
         {id: "ASM", tel: "1 684", name: "AMERICAN SAMOA"},
         {id: "AND", tel: "376", name: "ANDORRA"},
         {id: "AGO", tel: "244", name: "ANGOLA"},
         {id: "AIA", tel: "1 264", name: "ANGUILLA"},
         {id: "ATA", tel: "672", name: "ANTARCTICA"},
         {id: "ATG", tel: "1 268", name: "ANTIGUA AND BARBUDA"},
         {id: "ARG", tel: "54", name: "ARGENTINA"},
         {id: "ARM", tel: "374", name: "ARMENIA"},
         {id: "ABW", tel: "297", name: "ARUBA"},
         {id: "AUS", tel: "61", name: "AUSTRALIA"},
         {id: "AUT", tel: "43", name: "AUSTRIA"},
         {id: "AZE", tel: "994", name: "AZERBAIJAN"},
         {id: "BHS", tel: "1 242", name: "BAHAMAS"},
         {id: "BHR", tel: "973", name: "BAHRAIN"},
         {id: "BGD", tel: "880", name: "BANGLADESH"},
         {id: "BRB", tel: "1 246", name: "BARBADOS"},
         {id: "BLR", tel: "375", name: "BELARUS"},
         {id: "BEL", tel: "32", name: "BELGIUM"},
         {id: "BLZ", tel: "501", name: "BELIZE"},
         {id: "BEN", tel: "229", name: "BENIN"},
         {id: "BMU", tel: "1 441", name: "BERMUDA"},
         {id: "BTN", tel: "975", name: "BHUTAN"},
         {id: "BOL", tel: "591", name: "BOLIVIA"},
         {id: "BIH", tel: "387", name: "BOSNIA AND HERZEGOWINA"},
         {id: "BWA", tel: "267", name: "BOTSWANA"},
         {id: "BVT", tel: "61", name: "BOUVET ISLAND (Norway)"},
         {id: "BRA", tel: "55", name: "BRAZIL"},
         {id: "IOT", tel: "246", name: "BRITISH INDIAN OCEAN TERRITORY"},
         {id: "BRN", tel: "672", name: "BRUNEI DARUSSALAM"},
         {id: "BGR", tel: "359", name: "BULGARIA"},
         {id: "BFA", tel: "226", name: "BURKINA FASO"},
         {id: "BDI", tel: "257", name: "BURUNDI"},
         {id: "KHM", tel: "855", name: "CAMBODIA"},
         {id: "CMR", tel: "237", name: "CAMEROON"},
         {id: "CAN", tel: "1", name: "CANADA"},
         {id: "CPV", tel: "238", name: "CAPE VERDE"},
         {id: "CYM", tel: "1 345", name: "CAYMAN ISLANDS"},
         {id: "CAF", tel: "236", name: "CENTRAL AFRICAN REPUBLIC"},
         {id: "TCD", tel: "235", name: "CHAD"},
         {id: "CHL", tel: "56", name: "CHILE"},*/
        {id: "CHN", tel: "86", name: "CHINA"},
        /*{id: "CXR", tel: "61", name: "CHRISTMAS ISLAND"},
         {id: "CCK", tel: "891", name: "COCOS (KEELING) ISLANDS (Austrailia)"},
         {id: "COL", tel: "57", name: "COLOMBIA"},
         {id: "COM", tel: "269", name: "COMOROS"},
         {id: "COG", tel: "242", name: "CONGO"},
         {id: "COD", tel: "243", name: "CONGO, THE DRC"},
         {id: "COK", tel: "682", name: "COOK ISLANDS"},
         {id: "CRI", tel: "506", name: "COSTA RICA"},
         {id: "CIV", tel: "225", name: "COTE D'IVOIRE"},
         {id: "HRV", tel: "385", name: "CROATIA (local name: Hrvatska)"},
         {id: "CUB", tel: "53", name: "CUBA"},
         {id: "CYP", tel: "357", name: "CYPRUS"},
         {id: "CZE", tel: "420", name: "CZECH REPUBLIC"},
         {id: "DNK", tel: "45", name: "DENMARK"},
         {id: "DJI", tel: "253", name: "DJIBOUTI"},
         {id: "DMA", tel: "1 767", name: "DOMINICA"},
         {id: "DOM", tel: "1 809", name: "DOMINICAN REPUBLIC"},
         {id: "TMP", tel: "670", name: "EAST TIMOR"},
         {id: "ECU", tel: "593", name: "ECUADOR"},
         {id: "EGY", tel: "20", name: "EGYPT"},
         {id: "SLV", tel: "503", name: "EL SALVADOR"},
         {id: "GNQ", tel: "240", name: "EQUATORIAL GUINEA"},
         {id: "ERI", tel: "291", name: "ERITREA"},
         {id: "EST", tel: "372", name: "ESTONIA"},
         {id: "ETH", tel: "251", name: "ETHIOPIA"},
         {id: "FLK", tel: "500", name: "FALKLAND ISLANDS (MALVINAS)"},
         {id: "FRO", tel: "298", name: "FAROE ISLANDS"},
         {id: "FJI", tel: "679", name: "FIJI"},
         {id: "FIN", tel: "358", name: "FINLAND"},
         {id: "FRA", tel: "33", name: "FRANCE"},
         {id: "FXX", tel: "33", name: "FRANCE, METROPOLITAN"},
         {id: "GUF", tel: "594", name: "FRENCH GUIANA"},
         {id: "PYF", tel: "689", name: "FRENCH POLYNESIA"},
         {id: "ATF", tel: "262", name: "FRENCH SOUTHERN TERRITORIES"},
         {id: "GAB", tel: "241", name: "GABON"},
         {id: "GMB", tel: "220", name: "GAMBIA"},
         {id: "GEO", tel: "995", name: "GEORGIA"},
         {id: "DEU", tel: "49", name: "GERMANY"},
         {id: "GHA", tel: "233", name: "GHANA"},
         {id: "GIB", tel: "350", name: "GIBRALTAR"},
         {id: "GRC", tel: "30", name: "GREECE"},
         {id: "GRL", tel: "299", name: "GREENLAND"},
         {id: "GRD", tel: "1 473", name: "GRENADA"},
         {id: "GLP", tel: "590", name: "GUADELOUPE"},
         {id: "GUM", tel: "1 671", name: "GUAM"},
         {id: "GTM", tel: "502", name: "GUATEMALA"},
         {id: "GIN", tel: "224", name: "GUINEA"},
         {id: "GNB", tel: "245", name: "GUINEA-BISSAU"},
         {id: "GUY", tel: "592", name: "GUYANA"},
         {id: "HTI", tel: "509", name: "HAITI"},
         {id: "HMD", tel: "672", name: "HEARD AND MC DONALD ISLANDS"},
         {id: "VAT", tel: "379", name: "HOLY SEE (VATICAN CITY STATE)"},
         {id: "HND", tel: "504", name: "HONDURAS"},
         {id: "HKG", tel: "852", name: "HONG KONG"},
         {id: "HUN", tel: "36", name: "HUNGARY"},
         {id: "ISL", tel: "354", name: "ICELAND"},
         {id: "IND", tel: "91", name: "INDIA"},
         {id: "IDN", tel: "62", name: "INDONESIA"},
         {id: "IRN", tel: "98", name: "IRAN (ISLAMIC REPUBLIC OF)"},
         {id: "IRQ", tel: "964", name: "IRAQ"},
         {id: "IRL", tel: "353", name: "IRELAND"},
         {id: "ISR", tel: "972", name: "ISRAEL"},
         {id: "ITA", tel: "39", name: "ITALY"},
         {id: "JAM", tel: "1 876", name: "JAMAICA"},
         {id: "JPN", tel: "81", name: "JAPAN"},
         {id: "JOR", tel: "962", name: "JORDAN"},
         {id: "KAZ", tel: "7", name: "KAZAKHSTAN"},
         {id: "KEN", tel: "254", name: "KENYA"},
         {id: "KIR", tel: "686", name: "KIRIBATI"},
         {id: "PRK", tel: "850", name: "KOREA, D.P.R.O."},
         {id: "KOR", tel: "82", name: "KOREA, REPUBLIC OF"},
         {id: "KWT", tel: "965", name: "KUWAIT"},
         {id: "KGZ", tel: "996", name: "KYRGYZSTAN"},
         {id: "LAO", tel: "856", name: "LAOS"},
         {id: "LVA", tel: "371", name: "LATVIA"},
         {id: "LBN", tel: "961", name: "LEBANON"},
         {id: "LSO", tel: "266", name: "LESOTHO"},
         {id: "LBR", tel: "231", name: "LIBERIA"},
         {id: "LBY", tel: "218", name: "LIBYAN ARAB JAMAHIRIYA"},
         {id: "LIE", tel: "423", name: "LIECHTENSTEIN"},
         {id: "LTU", tel: "370", name: "LITHUANIA"},
         {id: "LUX", tel: "352", name: "LUXEMBOURG"},
         {id: "MAC", tel: "853", name: "MACAU"},
         {id: "MKD", tel: "389", name: "MACEDONIA"},
         {id: "MDG", tel: "261", name: "MADAGASCAR"},
         {id: "MWI", tel: "265", name: "MALAWI"},
         {id: "MYS", tel: "60", name: "MALAYSIA"},
         {id: "MDV", tel: "960", name: "MALDIVES"},
         {id: "MLI", tel: "223", name: "MALI"},
         {id: "MLT", tel: "356", name: "MALTA"},
         {id: "MHL", tel: "692", name: "MARSHALL ISLANDS"},
         {id: "MTQ", tel: "596", name: "MARTINIQUE"},
         {id: "MRT", tel: "222", name: "MAURITANIA"},
         {id: "MUS", tel: "230", name: "MAURITIUS"},
         {id: "MYT", tel: "262", name: "MAYOTTE"},
         {id: "MEX", tel: "52", name: "MEXICO"},
         {id: "FSM", tel: "691", name: "MICRONESIA, FEDERATED STATES OF"},
         {id: "MDA", tel: "373", name: "MOLDOVA REPUBLIC OF"},
         {id: "MCO", tel: "377", name: "MONACO"},
         {id: "MNG", tel: "976", name: "MONGOLIA"},
         {id: "MNE", tel: "382", name: "MONTENEGRO"},
         {id: "MSR", tel: "1 664", name: "MONTSERRAT"},
         {id: "MAR", tel: "212", name: "MOROCCO"},
         {id: "MOZ", tel: "258", name: "MOZAMBIQUE"},
         {id: "MMR", tel: "95", name: "MYANMAR (Burma)"},
         {id: "NAM", tel: "264", name: "NAMIBIA"},
         {id: "NRU", tel: "674", name: "NAURU"},
         {id: "NPL", tel: "977", name: "NEPAL"},
         {id: "NLD", tel: "31", name: "NETHERLANDS"},
         {id: "ANT", tel: "599", name: "NETHERLANDS ANTILLES"},
         {id: "NCL", tel: "687", name: "NEW CALEDONIA"},
         {id: "NZL", tel: "64", name: "NEW ZEALAND"},
         {id: "NIC", tel: "505", name: "NICARAGUA"},
         {id: "NER", tel: "227", name: "NIGER"},
         {id: "NGA", tel: "234", name: "NIGERIA"},
         {id: "NIU", tel: "683", name: "NIUE"},
         {id: "NFK", tel: "672", name: "NORFOLK ISLAND"},
         {id: "MNP", tel: "1 670", name: "NORTHERN MARIANA ISLANDS"},
         {id: "NOR", tel: "47", name: "NORWAY"},
         {id: "OMN", tel: "968", name: "OMAN"},
         {id: "PAK", tel: "92", name: "PAKISTAN"},
         {id: "PLW", tel: "680", name: "PALAU"},
         {id: "PAN", tel: "507", name: "PANAMA"},
         {id: "PNG", tel: "675", name: "PAPUA NEW GUINEA"},
         {id: "PRY", tel: "595", name: "PARAGUAY"},
         {id: "PER", tel: "51", name: "PERU"},
         {id: "PHL", tel: "63", name: "PHILIPPINES"},
         {id: "PCN", tel: "870", name: "PITCAIRN"},
         {id: "POL", tel: "48", name: "POLAND"},
         {id: "PRT", tel: "351", name: "PORTUGAL"},
         {id: "PRI", tel: "1", name: "PUERTO RICO"},
         {id: "QAT", tel: "974", name: "QATAR"},
         {id: "REU", tel: "262", name: "REUNION"},
         {id: "ROM", tel: "40", name: "ROMANIA"},
         {id: "RUS", tel: "7", name: "RUSSIAN FEDERATION"},
         {id: "RWA", tel: "250", name: "RWANDA"},
         {id: "KNA", tel: "1 869", name: "SAINT KITTS AND NEVIS"},
         {id: "LCA", tel: "1 758", name: "SAINT LUCIA"},
         {id: "VCT", tel: "1 784", name: "SAINT VINCENT AND THE GRENADINES"},
         {id: "WSM", tel: "685", name: "SAMOA"},
         {id: "SMR", tel: "378", name: "SAN MARINO"},
         {id: "STP", tel: "239", name: "SAO TOME AND PRINCIPE"},
         {id: "SAU", tel: "966", name: "SAUDI ARABIA"},
         {id: "SEN", tel: "221", name: "SENEGAL"},
         {id: "SRB", tel: "381", name: "SERBIA"},
         {id: "SYC", tel: "248", name: "SEYCHELLES"},
         {id: "SLE", tel: "232", name: "SIERRA LEONE"},
         {id: "SGP", tel: "65", name: "SINGAPORE"},
         {id: "SVK", tel: "421", name: "SLOVAKIA (Slovak Republic)"},
         {id: "SVN", tel: "386", name: "SLOVENIA"},
         {id: "SLB", tel: "677", name: "SOLOMON ISLANDS"},
         {id: "SOM", tel: "252", name: "SOMALIA"},
         {id: "ZAF", tel: "27", name: "SOUTH AFRICA"},
         {id: "SSD", tel: "211", name: "SOUTH SUDAN"},
         {id: "SGS", tel: "500", name: "SOUTH GEORGIA AND SOUTH S.S."},
         {id: "ESP", tel: "34", name: "SPAIN"},
         {id: "LKA", tel: "94", name: "SRI LANKA"},
         {id: "SHN", tel: "290", name: "ST. HELENA"},
         {id: "SPM", tel: "508", name: "ST. PIERRE AND MIQUELON"},
         {id: "SDN", tel: "249", name: "SUDAN"},
         {id: "SUR", tel: "597", name: "SURINAME"},
         {id: "SJM", tel: "47", name: "SVALBARDANDJAN MAYEN ISLANDS"},
         {id: "SWZ", tel: "268", name: "SWAZILAND"},
         {id: "SWE", tel: "46", name: "SWEDEN"},
         {id: "CHE", tel: "41", name: "SWITZERLAND"},
         {id: "SYR", tel: "963", name: "SYRIAN ARAB REPUBLIC"},
         {id: "TWN", tel: "886", name: "TAIWAN, PROVINCE OF CHINA"},
         {id: "TJK", tel: "992", name: "TAJIKISTAN"},
         {id: "TZA", tel: "255", name: "TANZANIA, UNITED REPUBLIC OF"},
         {id: "THA", tel: "66", name: "THAILAND"},
         {id: "TGO", tel: "228", name: "TOGO"},
         {id: "TKL", tel: "690", name: "TOKELAU"},
         {id: "TON", tel: "676", name: "TONGA"},
         {id: "TTO", tel: "1 868", name: "TRINIDAD AND TOBAGO"},
         {id: "TUN", tel: "216", name: "TUNISIA"},
         {id: "TUR", tel: "90", name: "TURKEY"},
         {id: "TKM", tel: "993", name: "TURKMENISTAN"},
         {id: "TCA", tel: "1 649", name: "TURKS AND CAICOS ISLANDS"},
         {id: "TUV", tel: "688", name: "TUVALU"},
         {id: "UGA", tel: "256", name: "UGANDA"},
         {id: "UKR", tel: "380", name: "UKRAINE"},
         {id: "ARE", tel: "971", name: "UNITED ARAB EMIRATES"},
         {id: "GBR", tel: "44", name: "UNITED KINGDOM"},
         {id: "USA", tel: "1", name: "UNITED STATES"},
         {id: "UMI", tel: "1", name: "U.S. MINOR ISLANDS"},
         {id: "URY", tel: "598", name: "URUGUAY"},
         {id: "UZB", tel: "998", name: "UZBEKISTAN"},
         {id: "VUT", tel: "678", name: "VANUATU"},
         {id: "VEN", tel: "58", name: "VENEZUELA"},
         {id: "VNM", tel: "84", name: "VIETNAM"},
         {id: "VGB", tel: "1 284", name: "VIRGIN ISLANDS (BRITISH)"},
         {id: "VIR", tel: "1 430", name: "VIRGIN ISLANDS (U.S.)"},
         {id: "WLF", tel: "681", name: "WALLIS AND FUTUNA ISLANDS"},
         {id: "ESH", tel: "212", name: "WESTERN SAHARA"},
         {id: "YEM", tel: "967", name: "YEMEN"},
         {id: "ZMB", tel: "260", name: "ZAMBIA"},
         {id: "ZWE", tel: "263", name: "ZIMBABWE"}*/
    ], function (c) {
        return c.id + " - " + c.name;
    }, new CodeTableSorter()),
    Province: new CodeTable([
        {id: "AH", pId: "CHN", text: "安徽省"},
        {id: "BJ", pId: "CHN", text: "北京市"},
        {id: "CQ", pId: "CHN", text: "重庆市"},
        {id: "FJ", pId: "CHN", text: "福建省"},
        {id: "GD", pId: "CHN", text: "广东省"},
        {id: "GS", pId: "CHN", text: "甘肃省"},
        {id: "GX", pId: "CHN", text: "广西壮族自治区"},
        {id: "GZ", pId: "CHN", text: "贵州省"},
        {id: "HA", pId: "CHN", text: "河南省"},
        {id: "HB", pId: "CHN", text: "湖北省"},
        {id: "HE", pId: "CHN", text: "河北省"},
        {id: "HI", pId: "CHN", text: "海南省"},
        {id: "HK", pId: "CHN", text: "香港特别行政区"},
        {id: "HL", pId: "CHN", text: "黑龙江省"},
        {id: "HN", pId: "CHN", text: "湖南省"},
        {id: "JL", pId: "CHN", text: "吉林省"},
        {id: "JS", pId: "CHN", text: "江苏省"},
        {id: "JX", pId: "CHN", text: "江西省"},
        {id: "LN", pId: "CHN", text: "辽宁省"},
        {id: "MO", pId: "CHN", text: "澳门特别行政区"},
        {id: "NM", pId: "CHN", text: "内蒙古自治区"},
        {id: "NX", pId: "CHN", text: "宁夏回族自治区"},
        {id: "QH", pId: "CHN", text: "青海省"},
        {id: "SC", pId: "CHN", text: "四川省"},
        {id: "SD", pId: "CHN", text: "山东省"},
        {id: "SH", pId: "CHN", text: "上海市"},
        {id: "SN", pId: "CHN", text: "陕西省"},
        {id: "SX", pId: "CHN", text: "山西省"},
        {id: "TJ", pId: "CHN", text: "天津市"},
        {id: "TW", pId: "CHN", text: "台湾省"},
        {id: "XJ", pId: "CHN", text: "新疆维吾尔自治区"},
        {id: "XZ", pId: "CHN", text: "西藏自治区"},
        {id: "YN", pId: "CHN", text: "云南省"},
        {id: "ZJ", pId: "CHN", text: "浙江省"}
    ], null, new CodeTableSorter()),
    City: new CodeTable([{id: "SH", pId: "SH", text: "上海"}]),
    District: new CodeTable([
        {id: "BS", pId: "SH", text: "宝山区"},
        {id: "CM", pId: "SH", text: "崇明县"},
        {id: "CN", pId: "SH", text: "长宁区"},
        {id: "FX", pId: "SH", text: "奉贤区"},
        {id: "HK", pId: "SH", text: "虹口区"},
        {id: "HP", pId: "SH", text: "黄浦区"},
        {id: "JA", pId: "SH", text: "静安区"},
        {id: "JD", pId: "SH", text: "嘉定区"},
        {id: "JS", pId: "SH", text: "金山区"},
        {id: "MH", pId: "SH", text: "闵行区"},
        {id: "NH", pId: "SH", text: "南汇区"},
        {id: "PD", pId: "SH", text: "浦东区"},
        {id: "PT", pId: "SH", text: "普陀区"},
        {id: "QP", pId: "SH", text: "青浦区"},
        {id: "SJ", pId: "SH", text: "松江区"},
        {id: "XH", pId: "SH", text: "徐汇区"},
        {id: "YP", pId: "SH", text: "杨浦区"},
        {id: "ZB", pId: "SH", text: "闸北区"}
    ])
}
