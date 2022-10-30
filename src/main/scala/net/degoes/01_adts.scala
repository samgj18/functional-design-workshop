package net.degoes

import java.time.Instant
import zio.Schedule

/*
 * INTRODUCTION
 *
 * Functional Design depends heavily on functional data modeling. Functional
 * data modeling is the task of creating precise, type-safe models of a given
 * domain using algebraic data types and generalized algebraic data types.
 *
 * In this section, you'll review basic functional domain modeling.
 */

/**
 * E-COMMERCE - EXERCISE SET 1
 *
 * Consider an e-commerce application that allows users to purchase products.
 */
object credit_card {

  /**
   * EXERCISE 1
   *
   * Using only sealed traits and case classes, create an immutable data model
   * of a credit card, which must have:
   *
   *  * Number
   *  * Name
   *  * Expiration date
   *  * Security code
   */
  type CreditCard

  /**
   * EXERCISE 2
   *
   * Using only sealed traits and case classes, create an immutable data model
   * of a product, which could be a physical product, such as a gallon of milk,
   * or a digital product, such as a book or movie, or access to an event, such
   * as a music concert or film showing.
   */
  sealed trait Product
  object Product {
    final case class Physical(name: String, description: String) extends Product
    final case class Digital(name: String, description: String)  extends Product
    final case class Event(name: String, description: String)    extends Product
  }

  /**
   * EXERCISE 3
   *
   * Using only sealed traits and case classes, create an immutable data model
   * of a product price, which could be one-time purchase fee, or a recurring
   * fee on some regular interval.
   */
  sealed trait PricingScheme
  object PricingScheme {
    final case class OneTime(amount: BigDecimal)                                extends PricingScheme
    final case class Recurring(amount: BigDecimal, interval: Schedule.Interval) extends PricingScheme
  }

  // This only has three terms, doesn't matter if Custom can be constructed in infinite ways.
  // what matters is that ChessColor can only be constructed in 3 ways.
  sealed trait ChessColor
  object ChessColor {
    case object White                                           extends ChessColor
    case object Black                                           extends ChessColor
    case class Custom(red: String, blue: String, green: String) extends ChessColor
  }
}

/**
 * EVENT PROCESSING - EXERCISE SET 3
 *
 * Consider an event processing application, which processes events from both
 * devices, as well as users.
 */
object events {

  /**
   * EXERCISE
   *
   * Refactor the object-oriented data model in this section to a more
   * functional one, which uses only sealed traits and case classes.
   */
  final case class Event(id: String, timestamp: Instant, data: Payload)

  sealed trait Payload
  final case class UserEvent(userName: String)   extends Payload
  final case class DeviceEvent(deviceId: String) extends Payload

  sealed trait UserPayload
  object UserPayload {
    final case class Purchase(item: String, price: Double) extends UserPayload
    case object AccountCreated                             extends UserPayload
  }

  sealed trait DevicePayload
  object DevicePayload {
    final case class SensorUpdated(reading: Option[Double]) extends DevicePayload
    case object DeviceActivated                             extends DevicePayload
  }

}

/**
 * DOCUMENT EDITING - EXERCISE SET 4
 *
 * Consider a web application that allows users to edit and store documents
 * of some type (which is not relevant for these exercises).
 */
object documents {
  final case class UserId(identifier: String)
  final case class DocId(identifier: String)
  final case class DocContent(body: String)

  /**
   * EXERCISE 1
   *
   * Using only sealed traits and case classes, create a simplified but somewhat
   * realistic model of a Document.
   */
  final case class Document(id: DocId, content: DocContent)

  /**
   * EXERCISE 2
   *
   * Using only sealed traits and case classes, create a model of the access
   * type that a given user might have with respect to a document. For example,
   * some users might have read-only permission on a document.
   */
  sealed trait AccessType
  object AccessType {
    case object Read  extends AccessType
    case object Write extends AccessType
    case object Admin extends AccessType
  }

  /**
   * EXERCISE 3
   *
   * Using only sealed traits and case classes, create a model of the
   * permissions that a user has on a set of documents they have access to.
   * Do not store the document contents themselves in this model.
   */
  final case class Permissions(permissions: Map[DocId, AccessType])
}

/**
 * BANKING - EXERCISE SET 5
 *
 * Consider a banking application that allows users to hold and transfer money.
 */
object bank {

  /**
   * EXERCISE 1
   *
   * Using only sealed traits and case classes, develop a model of a customer at a bank.
   */
  type Customer

  /**
   * EXERCISE 2
   *
   * Using only sealed traits and case classes, develop a model of an account
   * type. For example, one account type allows the user to write checks
   * against a given currency. Another account type allows the user to earn
   * interest at a given rate for the holdings in a given currency.
   */
  type AccountType

  /**
   * EXERCISE 3
   *
   * Using only sealed traits and case classes, develop a model of a bank
   * account, including details on the type of bank account, holdings, customer
   * who owns the bank account, and customers who have access to the bank account.
   */
  type Account
}

/**
 * STOCK PORTFOLIO - GRADUATION PROJECT
 *
 * Consider a web application that allows users to manage their portfolio of investments.
 */
object portfolio {

  /**
   * EXERCISE 1
   *
   * Using only sealed traits and case classes, develop a model of a stock
   * exchange. Ensure there exist values for NASDAQ and NYSE.
   */
  sealed trait Exchange
  object Exchange {
    case object NASDAQ extends Exchange
    case object NYSE   extends Exchange
  }

  /**
   * EXERCISE 2
   *
   * Using only sealed traits and case classes, develop a model of a currency
   * type.
   */
  sealed trait Currency
  object Currency {
    case object USD extends Currency
    case object EUR extends Currency
    case object GBP extends Currency
  }

  /**
   * EXERCISE 3
   *
   * Using only sealed traits and case classes, develop a model of a stock
   * symbol. Ensure there exists a value for Apple's stock (APPL).
   */
  final case class StockSymbol(id: String, exchange: Exchange)
  object StockSymbol {
    val APPL = StockSymbol("APPL", Exchange.NASDAQ)
  }

  /**
   * EXERCISE 4
   *
   * Using only sealed traits and case classes, develop a model of a portfolio
   * held by a user of the web application.
   */
  final case class Holding(shares: BigDecimal, purchasePrice: BigDecimal, currency: Currency)
  final case class Portfolio(id: String, holdings: Map[StockSymbol, Set[Holding]])

  /**
   * EXERCISE 5
   *
   * Using only sealed traits and case classes, develop a model of a user of
   * the web application.
   */
  final case class User(id: String, name: String, portfolio: Portfolio)

  /**
   * EXERCISE 6
   *
   * Using only sealed traits and case classes, develop a model of a trade type.
   * Example trade types might include Buy and Sell.
   */
  sealed trait TradeType
  object TradeType {
    case object Buy  extends TradeType
    case object Sell extends TradeType
  }

  /**
   * EXERCISE 7
   *
   * Using only sealed traits and case classes, develop a model of a trade,
   * which involves a particular trade type of a specific stock symbol at
   * specific prices.
   */
  final case class Trade(
    id: String,
    tradeType: TradeType,
    stockSymbol: StockSymbol,
    price: BigDecimal,
    quantity: BigDecimal
  )
}
