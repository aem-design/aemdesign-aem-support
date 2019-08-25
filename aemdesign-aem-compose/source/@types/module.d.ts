declare module '*.scss' {
  const content: { [className: string]: string };
  export = content;
}

declare module '*.vue' {
  import Vue from 'vue';

  export default Vue;
}

declare module 'owl.carousel';
