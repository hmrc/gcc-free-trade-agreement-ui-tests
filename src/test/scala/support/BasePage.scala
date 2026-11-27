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

import uk.gov.hmrc.configuration.TestEnvironment
import uk.gov.hmrc.selenium.component.PageObject
import uk.gov.hmrc.selenium.webdriver.Driver

import org.openqa.selenium.{By, WebDriver}
import org.openqa.selenium.support.ui.{ExpectedConditions, FluentWait, Wait}

import java.time.Duration

abstract class BasePage(pageUrl: String, pageTitle: String) extends PageObject {

  // URLs
  val baseUrl: String       = TestEnvironment.url("gcc-free-trade-agreement-frontend")
  val pageUrlFull: String   = s"$baseUrl$pageUrl"
  val pageTitleFull: String = s"$pageTitle - gcc-free-trade-agreement-frontend - GOV.UK"

  // Selectors
  val btnContinue: By = By.cssSelector("button.govuk-button")
  val inpTextBox: By  = By.cssSelector("input.govuk-input")

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

  def clickLink(linkText: String): Unit = click(By.cssSelector(linkText))

  def continue(): Unit = click(btnContinue)

  def clearAndSendKeys(value: String): Unit = sendKeys(inpTextBox, value)
}
