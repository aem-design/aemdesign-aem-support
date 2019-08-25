export default () => {
  const lists = document.querySelectorAll('[class*=list][component]')

  if (lists.length) {
    for (const list of lists) {
      const promotedCards = list.querySelectorAll('[class*="card--promoted-"]')

      if (promotedCards.length) {
        promotedCards[0].classList.add('card--promoted-set')
      }
    }
  }
}
