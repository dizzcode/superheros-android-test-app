package dizzcode.com.superheros

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import dizzcode.com.superheros.data.HeroesRepository
import dizzcode.com.superheros.model.Hero
import dizzcode.com.superheros.ui.theme.SuperheroesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SuperheroesTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .statusBarsPadding()
                        .navigationBarsPadding()
                ) {
                    SuperheroesApp()
                }
            }
        }
    }
}

@Composable
fun SuperheroesApp() {
    Scaffold(
        topBar = {
            AppTopAppBar()
        }
    ){ it ->
        LazyColumn(contentPadding = it) {
            items(HeroesRepository.heroes){ hero ->
                HeroItem(
                    hero = hero,
                    modifier = Modifier
                        .padding(dimensionResource(id = R.dimen.padding_small))
                )

            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopAppBar(modifier: Modifier = Modifier){
    CenterAlignedTopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_batman_logo),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground),
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.image_size_small))
                        .padding(dimensionResource(id = R.dimen.padding_small))
                )
                Text(
                    text = stringResource(id = R.string.app_name),
                    style = MaterialTheme.typography.displayLarge
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_batman_logo),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground),
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.image_size_small))
                        .padding(dimensionResource(id = R.dimen.padding_small))
                )

            }
        },
        modifier = modifier
    )
}


/**
 * Composable that displays a list item containing a hero icon and their information.
 *
 * @param hero contains the data that populates the list item
 * @param modifier modifiers to set to this composable
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeroItem(
    hero: Hero,
    modifier: Modifier = Modifier
) {

    var clicked by remember {
        mutableStateOf(false)
    }
    val color by animateColorAsState(
        targetValue = if(clicked) MaterialTheme.colorScheme.tertiaryContainer
        else MaterialTheme.colorScheme.secondaryContainer
    )

    ElevatedCard(
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessMedium
                )
            ),
        colors = CardDefaults.cardColors(color),
        onClick = { clicked = !clicked },
        elevation = CardDefaults.cardElevation(
            dimensionResource(id = R.dimen.card_elevation)
        ),
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_medium))
                .sizeIn(minHeight = 72.dp)

        ) {

            Row(
                modifier = modifier


            ) {

                //Spacer(modifier = Modifier.weight(0.5f))
                HeroInformation(
                    heroName = hero.nameRes,
                    heroInfo = hero.descriptionRes,
                    modifier = Modifier
                        .weight(3f)

                )

                HeroIcon(
                    heroIcon = hero.imageRes,
                    modifier = Modifier
                        .weight(1f)
                )
            }
        }
    }
}


/**
 * Composable that displays a hero's name and info.
 *
 * @param heroName is the resource ID for the string of the hero's name
 * @param heroInfo is the Int that represents the hero's info
 * @param modifier modifiers to set to this composable
 */
@Composable
fun HeroInformation(
    @StringRes heroName: Int,
    @StringRes heroInfo: Int,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(heroName),
            style = MaterialTheme.typography.displaySmall,
        )
        Text(
            text = stringResource(heroInfo),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

/**
 * Composable that displays a photo of a hero.
 *
 * @param heroIcon is the resource ID for the image of the hero
 * @param modifier modifiers to set to this composable
 */
@Composable
fun HeroIcon(
    @DrawableRes heroIcon: Int,
    modifier: Modifier = Modifier
) {
    Image(
        modifier = modifier
            .size(dimensionResource(R.dimen.image_size))
            .padding(start = dimensionResource(R.dimen.padding_medium))
            .clip(MaterialTheme.shapes.medium),
        painter = painterResource(heroIcon),
        contentScale = ContentScale.Fit,
        contentDescription = null
    )
}

@Preview("Light Theme")
@Preview("Dark Theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HeroItemPreview() {
    val hero = HeroesRepository.heroes[0]

    SuperheroesTheme {
        HeroItem(hero = hero)
    }
}


@Preview(name = "Light Theme",
    showBackground = true,
    showSystemUi = true
)
@Preview(name = "Dark Theme",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun SuperheroesAppPreview() {
    SuperheroesTheme {
        SuperheroesApp()
    }
}
