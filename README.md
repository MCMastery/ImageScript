# ImageScript
Programming language for generating images.

## Warning / Bugs
- At this current version (0.0.1), the edit menu is just a mock-up, and does not do anything yet.
- After closing the ImageViewer, you have to re-open it if you want to see the image again.

## Download
Download the executable JAR file from the repository (`ImageScript.jar`).

## Function Reference
| Function Name | Shortened Name | Description                               | Arguments                       |
|---------------|----------------|-------------------------------------------|---------------------------------|
| `fill`        | `f`            | Fills the image with the specified color. | color (Hex code)                |
| `size`        | `s`            | Sets the dimensions of the image.         | width (Number), height (Number) |
| `text`        | `t`            | Draws text at (0,0).                      | text (String)                   |

## Examples

### Hello world
`text("Hello, world!")`
or, using the shortened name:
`t("Hello, world!")`
