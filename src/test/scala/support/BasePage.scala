/*
 * Copyright 2023 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package support

// import uk.gov.hmrc.configuration.TestEnvironment // TO-DO: ADD WHEN AVAILABLE
import uk.gov.hmrc.selenium.component.PageObject
import uk.gov.hmrc.selenium.webdriver.Driver

import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.{FluentWait, Wait}
import org.openqa.selenium.support.ui.ExpectedConditions

import java.time.Duration

abstract class BasePage(pageUrl: String, pageTitle: String) extends PageObject {
    
    // URLs
    val baseUrl: String = "https://www.scalatest.org" // TO-DO: ADD WHEN AVAILABLE -- TestEnvironment.url("gcc-free-trade-agreement")
    val pageUrlFull: String = s"$baseUrl$pageUrl"
    val pageTitleFull: String = s"$pageTitle" // TO-DO: ADD WHEN AVAILABLE -- " - ServiceNameHere"

    // Common Functions
    def fluentWait: Wait[WebDriver] = new FluentWait[WebDriver](Driver.instance)
        .withTimeout(Duration.ofSeconds(10))
        .pollingEvery(Duration.ofSeconds(2))

    def navigateTo(urlToGet: String = pageUrlFull): Unit =
        get(urlToGet)

    def assertPageTitleFull(titleToCheck: String = pageTitleFull): Unit =
        assert(
            getTitle.contains(titleToCheck),
            s"Page title was [$getTitle], but it was expected to contain '[$titleToCheck]'."
        )

    def assertUrlFull(urlToCheck: String = pageUrlFull): Unit =
        fluentWait.until(ExpectedConditions.urlContains(urlToCheck))
        assert(getCurrentUrl == urlToCheck, s"Url was: [$getCurrentUrl], but [$urlToCheck] was expected.")
}
