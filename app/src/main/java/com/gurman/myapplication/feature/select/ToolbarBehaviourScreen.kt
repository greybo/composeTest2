package com.gurman.myapplication.feature.select

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CenterAlignedTopAppBarExample() {
//    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
//    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),

        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        "Centered Top App Bar",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Localized description"
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        },
    ) { innerPadding ->
        Column(modifier = Modifier
            .padding(innerPadding)
            .verticalScroll(rememberScrollState())) {
            Text(text = textForScroll)
        }
    }
}

val textForScroll = "\n" +
        "What is Lorem Ipsum?\n" +
        "Lorem Ipsum is simply dummy text of the printing and typesetting industry. " +
        "Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, " +
        "when an unknown printer took a galley of type and scrambled it to make a type specimen book. " +
        "It has survived not only five centuries, but also the leap into electronic typesetting, " +
        "remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset " +
        "sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like " +
        "Aldus PageMaker including versions of Lorem Ipsum.\n" +
        "\n" +
        "Why do we use it?\n" +
        "It is a long established fact that a reader will be distracted by the readable content of a page when " +
        "looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution " +
        "of letters, as opposed to using 'Content here, content here', making it look like readable English. " +
        "Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, " +
        "and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various versions " +
        "have evolved over the years, sometimes by accident, sometimes on purpose (injected humour and the like).\n" +
        "\n" +
        "\n" +
        "Where does it come from?\n" +
        "Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical " +
        "Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at " +
        "Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, " +
        "from a Lorem Ipsum passage, and going through the cites of the word in classical literature, " +
        "discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of " +
        "\"de Finibus Bonorum et Malorum\" (The Extremes of Good and Evil) by Cicero, written in 45 BC. " +
        "This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line " +
        "of Lorem Ipsum, \"Lorem ipsum dolor sit amet..\", comes from a line in section 1.10.32.\n" +
        "\n" +
        "The standard chunk of Lorem Ipsum used since the 1500s is reproduced below for those interested. " +
        "Sections 1.10.32 and 1.10.33 from \"de Finibus Bonorum et Malorum\" by Cicero are also reproduced in " +
        "their exact original form, accompanied by English versions from the 1914 translation by H. Rackham.\n" +
        "\n" +
        "Where can I get some?\n" +
        "There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration " +
        "in some form, by injected humour, or randomised words which don't look even slightly believable. " +
        "If you are going to use a passage of Lorem Ipsum, you need to be sure there isn't anything embarrassing " +
        "hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined " +
        "chunks as necessary, making this the first true generator on the Internet. It uses a dictionary of over " +
        "200 Latin words, combined with a handful of model sentence structures, to generate Lorem Ipsum which " +
        "looks reasonable. The generated Lorem Ipsum is therefore always free from repetition, injected humour, " +
        "or non-characteristic words etc."