package me.tatarka.androidgradletoolingapiissue;

import org.gradle.testkit.runner.GradleRunner;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@RunWith(JUnit4.class)
public class ToolingApiIssueTest {
    @Rule
    public final TemporaryFolder testProjectDir = new TemporaryFolder();
    private File buildFile;

    @Before
    public void setup() throws IOException {
        buildFile = testProjectDir.newFile("build.gradle");
    }

    @Test
    public void thisShouldPass() throws Exception {
        String buildFileContent = "buildscript {\n" +
                "    repositories {\n" +
                "        jcenter()\n" +
                "    }\n" +
                "\n" +
                "    dependencies {\n" +
                "        classpath 'com.android.tools.build:gradle:1.0.0'\n" +
                "    }\n" +
                "}\n" +
                "\n" +
                "apply plugin: 'com.android.application'\n" +
                "\n" +
                "repositories {\n" +
                "    mavenCentral()\n" +
                "}\n" +
                "\n" +
                "android {\n" +
                "    compileSdkVersion 23\n" +
                "    buildToolsVersion '23.0.1'\n" +
                "\n" +
                "    defaultConfig {\n" +
                "        minSdkVersion 15\n" +
                "        targetSdkVersion 23\n" +
                "    }\n" +
                "}";

        writeFile(buildFile, buildFileContent);

        File androidManifestFile = new File(testProjectDir.getRoot(), "src/main/AndroidManifest.xml");
        androidManifestFile.getParentFile().mkdirs();

        String androidManifestContent = "<manifest package=\"test\">\n" +
                "    <application/>\n" +
                "</manifest>";

        writeFile(androidManifestFile, androidManifestContent);

        File testFile = new File(testProjectDir.getRoot(), "src/androidTest/java/SimpleTest.java");
        testFile.getParentFile().mkdirs();

        String testContent = "import android.test.AndroidTestCase;\n" +
                "\n" +
                "public class SimpleTest extends AndroidTestCase {\n" +
                "    public void testNothing() {\n" +
                "    }\n" +
                "}";

        writeFile(testFile, testContent);

        GradleRunner.create()
                .withProjectDir(testProjectDir.getRoot())
                .withArguments("connectedCheck")
                .build();
    }

    private void writeFile(File destination, String content) throws IOException {
        BufferedWriter output = null;
        try {
            output = new BufferedWriter(new FileWriter(destination));
            output.write(content);
        } finally {
            if (output != null) {
                output.close();
            }
        }
    }
}
