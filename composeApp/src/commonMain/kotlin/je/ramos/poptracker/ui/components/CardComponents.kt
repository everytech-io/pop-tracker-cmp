package je.ramos.poptracker.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.ButtonGroup
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.IconButtonShapes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import je.ramos.poptracker.ui.theme.PopTrackerTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import poptracker.composeapp.generated.resources.Res
import poptracker.composeapp.generated.resources.icon_open_link
import poptracker.composeapp.generated.resources.labubu_demo
import poptracker.composeapp.generated.resources.lazada
import poptracker.composeapp.generated.resources.popmart
import poptracker.composeapp.generated.resources.shopee
import poptracker.composeapp.generated.resources.tiktok

@Immutable
data class ProductLinkItem(
    val productImage: Painter,
    val title: String,
    val subtitle: String,
    val websiteName: String,
    val websiteIcon: Painter,
    val price: String,
    val currency: String = "$",
    val priceRange: String? = null
)

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun SizableFilledTonalButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = ButtonDefaults.filledTonalShape,
) {

    val size = ButtonDefaults.ExtraSmallContainerHeight
    FilledTonalButton(
        onClick = onClick,
        modifier = modifier.heightIn(size),
        contentPadding = ButtonDefaults.contentPaddingFor(size),
        enabled = enabled,
        shape = shape,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmallEmphasized
        )
    }
}

@Composable
fun RowIcon(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.padding(horizontal = 8.dp).fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.size(24.dp).background(Color.Cyan),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                painter = painterResource(Res.drawable.popmart),
                contentDescription = "Popmart",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit
            )
        }
        Spacer(modifier = Modifier)
        SizableFilledTonalButton(
            text = "Buy Now",
            onClick = {},
        )
    }
}

@Preview
@Composable
fun RowIconPreview() {
    PopTrackerTheme {
        Column(
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        ) {
            RowIcon()
        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ProductLinkComponent(
    item: ProductLinkItem,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier.fillMaxWidth()
    ) {

        // Product info
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Product image
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)  // Makes it square (1:1 ratio)
            ) {
                Image(
                    painter = item.productImage,
                    contentDescription = item.title,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
            val description = "Localized description"

            Text(
                text = item.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Left,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(horizontal = 8.dp).fillMaxWidth()
            )
            Text(
                text = item.subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Left,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(horizontal = 8.dp).fillMaxWidth()
            )
            TooltipBox(
                positionProvider =
                    TooltipDefaults.rememberTooltipPositionProvider(),
                tooltip = { PlainTooltip { Text(description) } },
                state = rememberTooltipState(),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {

                val size = ButtonDefaults.ExtraSmallContainerHeight
                ElevatedButton(
                    onClick = { /* Do something! */ },
                    modifier = Modifier.heightIn(size),
                    contentPadding = ButtonDefaults.contentPaddingFor(size),
                ) {
                    Image(
                        painter = painterResource(Res.drawable.popmart),
                        contentDescription = description,
                        modifier = Modifier.width(64.dp)
                    )
                }
            }
            HorizontalDivider(modifier = Modifier.padding(horizontal = 8.dp))
            FlowRow(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                TooltipBox(
                    positionProvider =
                        TooltipDefaults.rememberTooltipPositionProvider(),
                    tooltip = { PlainTooltip { Text(description) } },
                    state = rememberTooltipState(),
                ) {
                    FilledTonalIconButton(
                        onClick = { /* doSomething() */ }
                    ) {
                        Image(
                            painter = painterResource(Res.drawable.shopee),
                            modifier = Modifier.size(24.dp),
                            contentDescription = description
                        )
                    }
                }
                TooltipBox(
                    positionProvider =
                        TooltipDefaults.rememberTooltipPositionProvider(),
                    tooltip = { PlainTooltip { Text(description) } },
                    state = rememberTooltipState(),
                ) {
                    FilledTonalIconButton(
                        onClick = { /* doSomething() */ }
                    ) {
                        Image(
                            painter = painterResource(Res.drawable.lazada),
                            modifier = Modifier.size(24.dp),
                            contentDescription = description
                        )
                    }
                }
                TooltipBox(
                    positionProvider =
                        TooltipDefaults.rememberTooltipPositionProvider(),
                    tooltip = { PlainTooltip { Text(description) } },
                    state = rememberTooltipState(),
                ) {
                    FilledTonalIconButton(
                        onClick = { /* doSomething() */ }
                    ) {
                        Image(
                            painter = painterResource(Res.drawable.tiktok),
                            modifier = Modifier.size(24.dp),
                            contentDescription = description
                        )
                    }
                }

            }


// Icon button should have a tooltip associated with it for a11y.
        }
    }
}

@Preview
@Composable
fun ProductLinkItemPreview() {
    PopTrackerTheme {
        Column(
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        ) {
            ProductLinkComponent(
                item = ProductLinkItem(
                    productImage = painterResource(Res.drawable.labubu_demo),
                    title = "Have a Seat",
                    subtitle = "THE MONSTER Vinyl Plush Blind box",
                    websiteName = "POPMART",
                    websiteIcon = painterResource(Res.drawable.popmart),
                    price = "900 - 5400",
                    currency = "P"
                )
            )
        }
    }
}

@Preview
@Composable
fun ProductGridPreview() {
    PopTrackerTheme {
        Surface {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 4.dp)
            ) {
                item {
                    ProductLinkComponent(
                        item = ProductLinkItem(
                            productImage = painterResource(Res.drawable.labubu_demo),
                            title = "Have a Seat",
                            subtitle = "THE MONSTER Vinyl Plush Blin",
                            websiteName = "POPMART",
                            websiteIcon = painterResource(Res.drawable.popmart),
                            price = "900 - 5400",
                            currency = "P"
                        )
                    )
                }
                item {
                    ProductLinkComponent(
                        item = ProductLinkItem(
                            productImage = painterResource(Res.drawable.labubu_demo),
                            title = "Have a Seat",
                            subtitle = "THE MONSTER Vinyl Plush Blin",
                            websiteName = "POPMART",
                            websiteIcon = painterResource(Res.drawable.popmart),
                            price = "900 - 5400",
                            currency = "P"
                        )
                    )
                }
                item {
                    ProductLinkComponent(
                        item = ProductLinkItem(
                            productImage = painterResource(Res.drawable.labubu_demo),
                            title = "Have a Seat",
                            subtitle = "THE MONSTER Vinyl Plush Blin",
                            websiteName = "POPMART",
                            websiteIcon = painterResource(Res.drawable.popmart),
                            price = "900 - 5400",
                            currency = "P"
                        )
                    )
                }
                item {
                    ProductLinkComponent(
                        item = ProductLinkItem(
                            productImage = painterResource(Res.drawable.labubu_demo),
                            title = "Have a Seat",
                            subtitle = "THE MONSTER Vinyl Plush Blin",
                            websiteName = "POPMART",
                            websiteIcon = painterResource(Res.drawable.popmart),
                            price = "900 - 5400",
                            currency = "P"
                        )
                    )
                }
            }
        }
    }
}
