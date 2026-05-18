@echo off

echo ==========================================
echo Generating Allure Report...
echo ==========================================

call mvn allure:report

echo ==========================================
echo Saving Allure History...
echo ==========================================

if exist target\site\allure-maven-plugin\history (

    if exist allure-history (
        rmdir /S /Q allure-history
    )

    mkdir allure-history

    xcopy /E /I /Y ^
    target\site\allure-maven-plugin\history ^
    allure-history

    echo Allure history saved successfully.

) else (

    echo No Allure history found to save.
)

echo ==========================================
echo Opening Allure Report...
echo ==========================================

call allure open target/site/allure-maven-plugin

pause