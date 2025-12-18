package com.gurman.myapplication.feature.product.data

import androidx.compose.ui.graphics.Color
import java.io.Serializable

enum class InvoiceSideType {
    Plus, Mines;

    val prefix
        get() = when (this) {
            Plus -> "+"
            Mines -> "—"
        }
    val color
        get() = when (this) {
            Plus -> Color(0xFF19A200)
            Mines -> Color(0xFFFF0000)
        }
}

enum class InvoiceMethodType {
    Excel, Immediately;

    val icon: Int
        get() = when (this) {
            Excel -> 1
            Immediately -> 2
        }
}

data class InvoiceItem(
    val id: String,
    val settings: InvoiceSettings,
    val sideType: InvoiceSideType,
    val method: InvoiceMethodType,
    val products: List<ProductItem>
) : Serializable

data class InvoiceSettings(
    val moreDetails: Boolean = false,
    val previewScreen: Boolean = true
) : Serializable

data class ProductItem(
    val id: String,
    val available: Boolean,
    val price: Int,
    val currency: String,
    val name: String,
    val quantityInStock: Int,
    val amount: Int,
    val vendor: String,
    val vendorCode: String,
    val barcode: String,
    val article: String,
    val description: String,
    val url: String,
    val imageUrl: String,
    val placeList: List<String>,
) : Serializable

val productItemFake = ProductItem(
    id = "id_3825",
    available = false,
    price = 826,
    currency = "UAH",
    name = "\"Сантек (ДЕЗ)\" засіб миючий, технічний, лужний",
    quantityInStock = 35,
    vendor = "No brand",
    vendorCode = "1640573497",
    barcode = "978020137963",
    article = "1640573497",
    description = "\"Сантек (ДЕЗ)\" - засіб миючий, лужний, технічний, високопінний з дезінфікуючим ефектом.\n\nЗастосовується для ручного та механічного миття водостійких поверхонь на підприємствах харчової промисловості, об'єктах громадського харчування, навчальних закладах.\n\nСклад: вода, ПАР, гліцерин, комплексно просвітителів, ялицеве масло.\n\nЗастосування: концентрація робочого розчину залежить від рівня забруднення і становить 1,0-2,0%. Для дуже забруднених поверхонь концентрація 50-100%. Робочий розчин необхідно нанести апаратом високого тиску або спінити вручну, протерти губкою або щіткою, витримати 15-20 хвилин. За потреби допускається використання не розлученого засобу. Після завершення обробки обладнання потрібно промити теплою водою до зникнення піни.\n\nУсі роботи із засобом проводити у захисних рукавичках та окулярах. У разі потрапляння засобу в очі чи слизові оболонки - промити невеликою кількістю води. У вічі закапати 1-2% краплями 30% розчином сульфацилу натрію, за необхідності звернутися до лікаря.\n\nТермін зберігання 12 місяців при t -10 + 35 С.\n\nФасування: у каністрах по 10 кг.",
    url = "https://gurman-vn.com/index.php?route=product/product&product_id=1479504338",
    imageUrl = "https://gurman-vn.com/image/catalog/photo/santek.png",
    amount = 0,
    placeList = listOf("B-10-02-14-01", "B-02-03-04-01"),
)


val invoiceItem = InvoiceItem(
    id = "1640573497",
    settings = InvoiceSettings(),
    products = listOf(productItemFake, productItemFake, productItemFake, productItemFake),
    sideType = InvoiceSideType.Plus,
    method = InvoiceMethodType.Excel
)