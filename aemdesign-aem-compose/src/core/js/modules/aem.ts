export default () => {
  const pageDetailsComponents = document.querySelectorAll('.page-details')

  if (pageDetailsComponents.length) {
    for (const pageDetails of pageDetailsComponents) {
      // Poor mans fix for any empty parsys in Page Details
      const parsys = pageDetails.querySelector('.container > .responsivegrid > .aem-Grid')

      if (parsys && !parsys.children.length) {
        parsys.classList.add('no-content')
      }
    }
  }
}
