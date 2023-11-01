package com.github.rhyrak;

import com.github.rhyrak.model.NISTEntry;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.github.rhyrak.model.Vulnerability;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/*
sort them based on three criteria: baseScore, impactScore, and exploitabilityScore. The
baseScore will be the most important criteria, which means the bigger baseScore will
provide more priority. If the baseScore of two vulnerabilities are the same, then you
need to compare their impactScore. If the baseScore and impactScore of two
vulnerabilities are the same, then you need to compare their exploitabilityScore.
If all three scores are the same, the lower CVE-ID wins
*/
class NISTEntryTest {

    @Test
    void compare_same_scores() {
        final String dummy1 = "{\"cve\":{\"id\":\"CVE-1999-0095\",\"sourceIdentifier\":\"cve@mitre.org\",\"published\":\"1988-10-01T04:00:00.000\",\"lastModified\":\"2019-06-11T20:29:00.263\",\"vulnStatus\":\"Modified\",\"descriptions\":[{\"lang\":\"en\",\"value\":\"The debug command in Sendmail is enabled, allowing attackers to execute commands as root.\"},{\"lang\":\"es\",\"value\":\"El comando de depuración de Sendmail está activado, permitiendo a atacantes ejecutar comandos como root.\"}],\"metrics\":{\"cvssMetricV2\":[{\"source\":\"nvd@nist.gov\",\"type\":\"Primary\",\"cvssData\":{\"version\":\"2.0\",\"vectorString\":\"AV:N/AC:L/Au:N/C:C/I:C/A:C\",\"accessVector\":\"NETWORK\",\"accessComplexity\":\"LOW\",\"authentication\":\"NONE\",\"confidentialityImpact\":\"COMPLETE\",\"integrityImpact\":\"COMPLETE\",\"availabilityImpact\":\"COMPLETE\","
                + "\"baseScore\":10},\"baseSeverity\":\"HIGH\","
                + "\"exploitabilityScore\":10,"
                + "\"impactScore\":10,\"acInsufInfo\":false,\"obtainAllPrivilege\":true,\"obtainUserPrivilege\":false,\"obtainOtherPrivilege\":false,\"userInteractionRequired\":false}]},\"weaknesses\":[{\"source\":\"nvd@nist.gov\",\"type\":\"Primary\",\"description\":[{\"lang\":\"en\",\"value\":\"NVD-CWE-Other\"}]}],\"configurations\":[{\"nodes\":[{\"operator\":\"OR\",\"negate\":false,\"cpeMatch\":[{\"vulnerable\":true,\"criteria\":\"cpe:2.3:a:eric_allman:sendmail:5.58:*:*:*:*:*:*:*\",\"matchCriteriaId\":\"1D07F493-9C8D-44A4-8652-F28B46CBA27C\"}]}]}],\"references\":[{\"url\":\"http://seclists.org/fulldisclosure/2019/Jun/16\",\"source\":\"cve@mitre.org\"},{\"url\":\"http://www.openwall.com/lists/oss-security/2019/06/05/4\",\"source\":\"cve@mitre.org\"},{\"url\":\"http://www.openwall.com/lists/oss-security/2019/06/06/1\",\"source\":\"cve@mitre.org\"},{\"url\":\"http://www.osvdb.org/195\",\"source\":\"cve@mitre.org\"},{\"url\":\"http://www.securityfocus.com/bid/1\",\"source\":\"cve@mitre.org\"}]}}";
        final String dummy2 = "{\"cve\":{\"id\":\"CVE-1999-0082\",\"sourceIdentifier\":\"cve@mitre.org\",\"published\":\"1988-11-11T05:00:00.000\",\"lastModified\":\"2008-09-09T12:33:40.853\",\"vulnStatus\":\"Analyzed\",\"descriptions\":[{\"lang\":\"en\",\"value\":\"CWD ~root command in ftpd allows root access.\"}],\"metrics\":{\"cvssMetricV2\":[{\"source\":\"nvd@nist.gov\",\"type\":\"Primary\",\"cvssData\":{\"version\":\"2.0\",\"vectorString\":\"AV:N/AC:L/Au:N/C:C/I:C/A:C\",\"accessVector\":\"NETWORK\",\"accessComplexity\":\"LOW\",\"authentication\":\"NONE\",\"confidentialityImpact\":\"COMPLETE\",\"integrityImpact\":\"COMPLETE\",\"availabilityImpact\":\"COMPLETE\","
                + "\"baseScore\":10},\"baseSeverity\":\"HIGH\","
                + "\"exploitabilityScore\":10,"
                + "\"impactScore\":10,\"acInsufInfo\":false,\"obtainAllPrivilege\":true,\"obtainUserPrivilege\":false,\"obtainOtherPrivilege\":false,\"userInteractionRequired\":false}]},\"weaknesses\":[{\"source\":\"nvd@nist.gov\",\"type\":\"Primary\",\"description\":[{\"lang\":\"en\",\"value\":\"NVD-CWE-Other\"}]}],\"configurations\":[{\"nodes\":[{\"operator\":\"OR\",\"negate\":false,\"cpeMatch\":[{\"vulnerable\":true,\"criteria\":\"cpe:2.3:a:ftp:ftp:*:*:*:*:*:*:*:*\",\"matchCriteriaId\":\"30D7F58F-4C55-4D19-984C-79B6C9525BEB\"},{\"vulnerable\":true,\"criteria\":\"cpe:2.3:a:ftpcd:ftpcd:*:*:*:*:*:*:*:*\",\"matchCriteriaId\":\"1D85A7F5-C187-4707-8681-F96A91F58318\"}]}]}],\"references\":[{\"url\":\"http://www.alw.nih.gov/Security/Docs/admin-guide-to-cracking.101.html\",\"source\":\"cve@mitre.org\"}]}}";

        Gson parser = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
        Vulnerability v1 = parser.fromJson(dummy1, Vulnerability.class);
        Vulnerability v2 = parser.fromJson(dummy2, Vulnerability.class);

        NISTEntry e1 = new NISTEntry(v1);
        NISTEntry e2 = new NISTEntry(v2);

        Assertions.assertEquals(e1.getId().compareTo(e2.getId()), e1.compareTo(e2));
        logEntries(e1, e2);
    }

    @Test
    void compare_base_score() {
        final String dummy1 = "{\"cve\":{\"id\":\"xxxxxx\",\"sourceIdentifier\":\"cve@mitre.org\",\"published\":\"1988-10-01T04:00:00.000\",\"lastModified\":\"2019-06-11T20:29:00.263\",\"vulnStatus\":\"Modified\",\"descriptions\":[{\"lang\":\"en\",\"value\":\"The debug command in Sendmail is enabled, allowing attackers to execute commands as root.\"},{\"lang\":\"es\",\"value\":\"El comando de depuración de Sendmail está activado, permitiendo a atacantes ejecutar comandos como root.\"}],\"metrics\":{\"cvssMetricV2\":[{\"source\":\"nvd@nist.gov\",\"type\":\"Primary\",\"cvssData\":{\"version\":\"2.0\",\"vectorString\":\"AV:N/AC:L/Au:N/C:C/I:C/A:C\",\"accessVector\":\"NETWORK\",\"accessComplexity\":\"LOW\",\"authentication\":\"NONE\",\"confidentialityImpact\":\"COMPLETE\",\"integrityImpact\":\"COMPLETE\",\"availabilityImpact\":\"COMPLETE\","
                + "\"baseScore\":15},\"baseSeverity\":\"HIGH\","
                + "\"exploitabilityScore\":10,"
                + "\"impactScore\":10,\"acInsufInfo\":false,\"obtainAllPrivilege\":true,\"obtainUserPrivilege\":false,\"obtainOtherPrivilege\":false,\"userInteractionRequired\":false}]},\"weaknesses\":[{\"source\":\"nvd@nist.gov\",\"type\":\"Primary\",\"description\":[{\"lang\":\"en\",\"value\":\"NVD-CWE-Other\"}]}],\"configurations\":[{\"nodes\":[{\"operator\":\"OR\",\"negate\":false,\"cpeMatch\":[{\"vulnerable\":true,\"criteria\":\"cpe:2.3:a:eric_allman:sendmail:5.58:*:*:*:*:*:*:*\",\"matchCriteriaId\":\"1D07F493-9C8D-44A4-8652-F28B46CBA27C\"}]}]}],\"references\":[{\"url\":\"http://seclists.org/fulldisclosure/2019/Jun/16\",\"source\":\"cve@mitre.org\"},{\"url\":\"http://www.openwall.com/lists/oss-security/2019/06/05/4\",\"source\":\"cve@mitre.org\"},{\"url\":\"http://www.openwall.com/lists/oss-security/2019/06/06/1\",\"source\":\"cve@mitre.org\"},{\"url\":\"http://www.osvdb.org/195\",\"source\":\"cve@mitre.org\"},{\"url\":\"http://www.securityfocus.com/bid/1\",\"source\":\"cve@mitre.org\"}]}}";
        final String dummy2 = "{\"cve\":{\"id\":\"xxxxxx\",\"sourceIdentifier\":\"cve@mitre.org\",\"published\":\"1988-11-11T05:00:00.000\",\"lastModified\":\"2008-09-09T12:33:40.853\",\"vulnStatus\":\"Analyzed\",\"descriptions\":[{\"lang\":\"en\",\"value\":\"CWD ~root command in ftpd allows root access.\"}],\"metrics\":{\"cvssMetricV2\":[{\"source\":\"nvd@nist.gov\",\"type\":\"Primary\",\"cvssData\":{\"version\":\"2.0\",\"vectorString\":\"AV:N/AC:L/Au:N/C:C/I:C/A:C\",\"accessVector\":\"NETWORK\",\"accessComplexity\":\"LOW\",\"authentication\":\"NONE\",\"confidentialityImpact\":\"COMPLETE\",\"integrityImpact\":\"COMPLETE\",\"availabilityImpact\":\"COMPLETE\","
                + "\"baseScore\":10},\"baseSeverity\":\"HIGH\","
                + "\"exploitabilityScore\":10,"
                + "\"impactScore\":10,\"acInsufInfo\":false,\"obtainAllPrivilege\":true,\"obtainUserPrivilege\":false,\"obtainOtherPrivilege\":false,\"userInteractionRequired\":false}]},\"weaknesses\":[{\"source\":\"nvd@nist.gov\",\"type\":\"Primary\",\"description\":[{\"lang\":\"en\",\"value\":\"NVD-CWE-Other\"}]}],\"configurations\":[{\"nodes\":[{\"operator\":\"OR\",\"negate\":false,\"cpeMatch\":[{\"vulnerable\":true,\"criteria\":\"cpe:2.3:a:ftp:ftp:*:*:*:*:*:*:*:*\",\"matchCriteriaId\":\"30D7F58F-4C55-4D19-984C-79B6C9525BEB\"},{\"vulnerable\":true,\"criteria\":\"cpe:2.3:a:ftpcd:ftpcd:*:*:*:*:*:*:*:*\",\"matchCriteriaId\":\"1D85A7F5-C187-4707-8681-F96A91F58318\"}]}]}],\"references\":[{\"url\":\"http://www.alw.nih.gov/Security/Docs/admin-guide-to-cracking.101.html\",\"source\":\"cve@mitre.org\"}]}}";

        Gson parser = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
        Vulnerability v1 = parser.fromJson(dummy1, Vulnerability.class);
        Vulnerability v2 = parser.fromJson(dummy2, Vulnerability.class);

        NISTEntry e1 = new NISTEntry(v1);
        NISTEntry e2 = new NISTEntry(v2);

        int expected = 0;
        if (e1.getBaseScore() > e2.getBaseScore())
            expected = 1;
        if (e1.getBaseScore() < e2.getBaseScore())
            expected = -1;

        Assertions.assertEquals(expected, e1.compareTo(e2));
        logEntries(e1, e2);
    }

    static void logEntries(NISTEntry e1, NISTEntry e2) {
        System.out.println("               Entry 1        Entry 2");
        System.out.printf("Base score:    %-7.2f        %-7.2f\n", e1.getBaseScore(), e2.getBaseScore());
        System.out.printf("Impact score:  %-7.2f        %-7.2f\n", e1.getImpactScore(), e2.getImpactScore());
        System.out.printf("Explo. score:  %-7.2f        %-7.2f\n", e1.getExploitabilityScore(), e2.getExploitabilityScore());
        System.out.printf("ID:            %-14s %s\n", e1.getId(), e2.getId());
        System.out.printf("e1.comp(e2):   %2d\n", e1.compareTo(e2));
        System.out.printf("e2.comp(e1):   %2d\n", e2.compareTo(e1));
    }
}