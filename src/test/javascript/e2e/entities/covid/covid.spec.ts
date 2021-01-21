import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { CovidComponentsPage, CovidDeleteDialog, CovidUpdatePage } from './covid.page-object';

const expect = chai.expect;

describe('Covid e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let covidComponentsPage: CovidComponentsPage;
  let covidUpdatePage: CovidUpdatePage;
  let covidDeleteDialog: CovidDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Covids', async () => {
    await navBarPage.goToEntity('covid');
    covidComponentsPage = new CovidComponentsPage();
    await browser.wait(ec.visibilityOf(covidComponentsPage.title), 5000);
    expect(await covidComponentsPage.getTitle()).to.eq('Covids');
    await browser.wait(ec.or(ec.visibilityOf(covidComponentsPage.entities), ec.visibilityOf(covidComponentsPage.noResult)), 1000);
  });

  it('should load create Covid page', async () => {
    await covidComponentsPage.clickOnCreateButton();
    covidUpdatePage = new CovidUpdatePage();
    expect(await covidUpdatePage.getPageTitle()).to.eq('Create or edit a Covid');
    await covidUpdatePage.cancel();
  });

  it('should create and save Covids', async () => {
    const nbButtonsBeforeCreate = await covidComponentsPage.countDeleteButtons();

    await covidComponentsPage.clickOnCreateButton();

    await promise.all([
      covidUpdatePage.setNombretestInput('nombretest'),
      covidUpdatePage.setPositifcasInput('positifcas'),
      covidUpdatePage.setDateInput('2000-12-31'),
    ]);

    expect(await covidUpdatePage.getNombretestInput()).to.eq('nombretest', 'Expected Nombretest value to be equals to nombretest');
    expect(await covidUpdatePage.getPositifcasInput()).to.eq('positifcas', 'Expected Positifcas value to be equals to positifcas');
    expect(await covidUpdatePage.getDateInput()).to.eq('2000-12-31', 'Expected date value to be equals to 2000-12-31');

    await covidUpdatePage.save();
    expect(await covidUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await covidComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Covid', async () => {
    const nbButtonsBeforeDelete = await covidComponentsPage.countDeleteButtons();
    await covidComponentsPage.clickOnLastDeleteButton();

    covidDeleteDialog = new CovidDeleteDialog();
    expect(await covidDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Covid?');
    await covidDeleteDialog.clickOnConfirmButton();

    expect(await covidComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
